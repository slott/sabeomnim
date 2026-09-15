package com.sabeomnim.app.core.ui.belt

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sabeomnim.app.data.models.BeltRank
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * 2D Particle node for cloth physics simulation.
 */
class BeltParticle(
    var x: Float,
    var y: Float,
    var prevX: Float = x,
    var prevY: Float = y,
    val isAnchor: Boolean = false
) {
    fun reset(posX: Float, posY: Float) {
        x = posX
        y = posY
        prevX = posX
        prevY = posY
    }

    fun applyImpulse(vx: Float, vy: Float) {
        if (!isAnchor) {
            prevX = x - vx
            prevY = y - vy
        }
    }
}

/**
 * Represents one hanging tail of a martial arts belt.
 */
class BeltTail(
    val isRightTail: Boolean,
    val numSegments: Int = 6
) {
    val particles = ArrayList<BeltParticle>(numSegments + 1)
    var currentRestLength: Float = 0f
    var targetLength: Float = 0f

    init {
        for (i in 0..numSegments) {
            particles.add(BeltParticle(0f, 0f, isAnchor = (i == 0)))
        }
    }

    val segmentRestLength: Float
        get() = currentRestLength / numSegments

    fun setup(anchorX: Float, anchorY: Float, length: Float) {
        targetLength = length
        currentRestLength = length
        val segLen = length / numSegments
        particles[0].reset(anchorX, anchorY)

        for (i in 1..numSegments) {
            particles[i].reset(anchorX, anchorY + (i * segLen))
        }
    }
}

/**
 * 2D Physics Simulator for Taekwondo belt tails.
 * Implements Verlet cloth physics, natural vertical fabric drape,
 * harmonic oscillation, and interactive touch/drag response.
 */
class BeltPhysicsSystem(
    val density: Float,
    val boxWidthPx: Float,
    val totalHeightPx: Float,
    val tailWidthPx: Float
) {
    val leftTail = BeltTail(isRightTail = false, numSegments = 6)
    val rightTail = BeltTail(isRightTail = true, numSegments = 6)

    private var draggedTail: BeltTail? = null
    private var draggedParticleIndex: Int = -1
    private var lastDragX: Float = 0f
    private var lastDragY: Float = 0f
    private var dragVelX: Float = 0f
    private var dragVelY: Float = 0f

    val gravityY = 3200f * density
    val damping = 0.985f
    val fabricStiffness = 45f // Natural vertical restoring spring

    fun initialize(knotCenterX: Float, knotAnchorY: Float, fullLengthPx: Float) {
        val anchorSpacing = 13.dp.value * density
        val leftAnchorX = knotCenterX - (anchorSpacing / 2f)
        val rightAnchorX = knotCenterX + (anchorSpacing / 2f)

        val leftLen = fullLengthPx * 0.96f
        val rightLen = fullLengthPx * 0.93f

        leftTail.setup(leftAnchorX, knotAnchorY, leftLen)
        rightTail.setup(rightAnchorX, knotAnchorY, rightLen)
    }

    fun onUnfoldProgress(progress: Float, fullLengthPx: Float) {
        val leftTarget = fullLengthPx * 0.96f * progress
        val rightTarget = fullLengthPx * 0.93f * progress
        leftTail.currentRestLength = max(4f, leftTarget)
        rightTail.currentRestLength = max(4f, rightTarget)

        // Gentle parting wave while unfolding from knot
        if (progress in 0.05f..0.85f) {
            val wave = sin(progress * PI.toFloat()) * 3.5f * density
            leftTail.particles.last().applyImpulse(-wave * 0.25f, 0f)
            rightTail.particles.last().applyImpulse(wave * 0.25f, 0f)
        }
    }

    fun onDragStart(touchX: Float, touchY: Float): Boolean {
        val maxRadius = 50.dp.value * density
        var closestDist = Float.MAX_VALUE
        var foundTail: BeltTail? = null
        var foundIndex = -1

        // Look for movable nodes (i >= 1)
        for (tail in listOf(rightTail, leftTail)) {
            for (i in 1 until tail.particles.size) {
                val p = tail.particles[i]
                val d = hypot(p.x - touchX, p.y - touchY)
                if (d < maxRadius && d < closestDist) {
                    closestDist = d
                    foundTail = tail
                    foundIndex = i
                }
            }
        }

        if (foundTail != null && foundIndex >= 1) {
            draggedTail = foundTail
            draggedParticleIndex = foundIndex
            lastDragX = touchX
            lastDragY = touchY
            dragVelX = 0f
            dragVelY = 0f

            foundTail.particles[foundIndex].x = touchX
            foundTail.particles[foundIndex].y = touchY
            return true
        }
        return false
    }

    fun onDrag(touchX: Float, touchY: Float) {
        val tail = draggedTail ?: return
        val idx = draggedParticleIndex
        if (idx >= 1 && idx < tail.particles.size) {
            dragVelX = (touchX - lastDragX) * 0.85f
            dragVelY = (touchY - lastDragY) * 0.85f
            lastDragX = touchX
            lastDragY = touchY

            tail.particles[idx].x = touchX
            tail.particles[idx].y = touchY
        }
    }

    fun onDragEnd() {
        val tail = draggedTail
        val idx = draggedParticleIndex
        if (tail != null && idx >= 1 && idx < tail.particles.size) {
            // Impart release swing momentum
            tail.particles[idx].applyImpulse(dragVelX * 8f, dragVelY * 5f)
        }
        draggedTail = null
        draggedParticleIndex = -1
    }

    fun onTap(touchX: Float, touchY: Float) {
        for (tail in listOf(leftTail, rightTail)) {
            val tip = tail.particles.last()
            val impulse = if (touchX >= tip.x) 280f * density else -280f * density
            for (i in 2 until tail.particles.size) {
                val weight = (i.toFloat() / tail.particles.size)
                tail.particles[i].applyImpulse(impulse * weight, 0f)
            }
        }
    }

    fun update(dt: Float) {
        val tails = listOf(leftTail, rightTail)

        // 1. Verlet Integration
        for (tail in tails) {
            val anchor = tail.particles[0]
            val isBeingDragged = (tail == draggedTail)

            for (i in 1 until tail.particles.size) {
                if (isBeingDragged && i == draggedParticleIndex) continue

                val p = tail.particles[i]
                val vx = (p.x - p.prevX) * damping
                val vy = (p.y - p.prevY) * damping

                p.prevX = p.x
                p.prevY = p.y

                // Natural cloth drape: fabric stiffness pulls gently towards vertical line below anchor
                val restoringForceX = (anchor.x - p.x) * fabricStiffness

                p.x += vx + (restoringForceX * dt * dt)
                p.y += vy + (gravityY * dt * dt)
            }
        }

        // 2. Distance Constraints Relaxation (5 iterations)
        for (iter in 0 until 5) {
            for (tail in tails) {
                val segLen = tail.segmentRestLength

                for (i in 0 until tail.particles.size - 1) {
                    val p1 = tail.particles[i]
                    val p2 = tail.particles[i + 1]

                    val isP1Fixed = p1.isAnchor || (tail == draggedTail && i == draggedParticleIndex)
                    val isP2Fixed = (tail == draggedTail && (i + 1) == draggedParticleIndex)

                    val dx = p2.x - p1.x
                    val dy = p2.y - p1.y
                    val dist = max(0.001f, hypot(dx, dy))
                    val diff = (dist - segLen) / dist

                    if (isP1Fixed && !isP2Fixed) {
                        p2.x -= dx * diff
                        p2.y -= dy * diff
                    } else if (!isP1Fixed && isP2Fixed) {
                        p1.x += dx * diff
                        p1.y += dy * diff
                    } else if (!isP1Fixed && !isP2Fixed) {
                        p1.x += dx * diff * 0.5f
                        p1.y += dy * diff * 0.5f
                        p2.x -= dx * diff * 0.5f
                        p2.y -= dy * diff * 0.5f
                    }
                }
            }
        }
    }
}

/**
 * Authentic Taekwondo Martial Arts Belt (Tti) with two hanging tails,
 * flat rectangular finished ends, single-tail rank color tab, and interactive 2D physics.
 */
@Composable
fun UnfoldingBeltView(
    belt: BeltRank,
    modifier: Modifier = Modifier,
    beltWidth: Dp = 48.dp,
    maxBeltLength: Dp = 175.dp,
    autoPlay: Boolean = true
) {
    val density = LocalDensity.current.density
    val unfoldProgress = remember { Animatable(0f) }

    val boxWidth = 74.dp
    val boxHeight = maxBeltLength + 28.dp

    val boxWidthPx = boxWidth.value * density
    val totalHeightPx = boxHeight.value * density
    val tailWidthPx = 18.5f * density
    val knotCenterXPx = boxWidthPx / 2f
    val knotHeightPx = 21.dp.value * density
    val knotAnchorY = knotHeightPx * 0.70f
    val fullTailLenPx = maxBeltLength.value * density

    // Create and remember the 2D physics system
    val physics = remember {
        BeltPhysicsSystem(
            density = density,
            boxWidthPx = boxWidthPx,
            totalHeightPx = totalHeightPx,
            tailWidthPx = tailWidthPx
        ).apply {
            initialize(knotCenterXPx, knotAnchorY, fullTailLenPx)
        }
    }

    // Trigger unfolding animation when belt changes
    LaunchedEffect(belt) {
        physics.initialize(knotCenterXPx, knotAnchorY, fullTailLenPx)
        if (autoPlay) {
            unfoldProgress.snapTo(0.10f)
            unfoldProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1100, easing = FastOutSlowInEasing)
            )
        } else {
            unfoldProgress.snapTo(1f)
        }
    }

    // Real-time physics simulation loop
    var frameTick by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        var lastNanos = 0L
        while (isActive) {
            withFrameNanos { timeNanos ->
                if (lastNanos != 0L) {
                    val dt = ((timeNanos - lastNanos) / 1_000_000_000f).coerceIn(0.005f, 0.033f)
                    physics.onUnfoldProgress(unfoldProgress.value, fullTailLenPx)
                    physics.update(dt)
                    frameTick = timeNanos
                }
                lastNanos = timeNanos
            }
        }
    }

    val baseBeltColor = Color(belt.colorHex)
    val accentBeltColor = Color(belt.accentColorHex)
    val isStripeBelt = belt.name.contains("STRIPE")
    val isBlackBelt = belt == BeltRank.BLACK

    Box(
        modifier = modifier
            .width(boxWidth)
            .height(boxHeight)
            .pointerInput(belt) {
                detectTapGestures { offset ->
                    physics.onTap(offset.x, offset.y)
                }
            }
            .pointerInput(belt) {
                detectDragGestures(
                    onDragStart = { offset ->
                        physics.onDragStart(offset.x, offset.y)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        physics.onDrag(change.position.x, change.position.y)
                    },
                    onDragEnd = {
                        physics.onDragEnd()
                    },
                    onDragCancel = {
                        physics.onDragEnd()
                    }
                )
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (frameTick == -1L) return@Canvas

            val knotWidthPx = 52.dp.toPx()
            val knotLeftPx = (size.width - knotWidthPx) / 2f

            // 1. Draw Left Tail (Plain cotton tail, no rank tab)
            drawBeltTail(
                tail = physics.leftTail,
                tailWidthPx = tailWidthPx,
                baseColor = baseBeltColor,
                accentColor = accentBeltColor,
                stitchColor = getStitchColor(belt),
                hasRankTab = false,
                isStripeBelt = false,
                isBlackBelt = false
            )

            // 2. Draw Right Tail (Front tail with authentic color tab / Dan bar)
            drawBeltTail(
                tail = physics.rightTail,
                tailWidthPx = tailWidthPx,
                baseColor = baseBeltColor,
                accentColor = accentBeltColor,
                stitchColor = getStitchColor(belt),
                hasRankTab = true,
                isStripeBelt = isStripeBelt,
                isBlackBelt = isBlackBelt
            )

            // 3. Draw Belt Knot at top (Square knot anchoring both tails)
            drawBeltKnot(
                knotLeft = knotLeftPx,
                knotWidth = knotWidthPx,
                knotHeight = knotHeightPx,
                baseColor = baseBeltColor,
                accentColor = accentBeltColor,
                isStripeBelt = isStripeBelt
            )
        }
    }
}

/**
 * Draws a hanging belt tail ribbon with flat rectangular end, longitudinal stitches,
 * and optional rank stripe on the right tail.
 */
private fun DrawScope.drawBeltTail(
    tail: BeltTail,
    tailWidthPx: Float,
    baseColor: Color,
    accentColor: Color,
    stitchColor: Color,
    hasRankTab: Boolean,
    isStripeBelt: Boolean,
    isBlackBelt: Boolean
) {
    val particles = tail.particles
    if (particles.size < 2) return

    val numNodes = particles.size
    val halfWidth = tailWidthPx / 2f

    // Calculate smoothed normal vectors at each node
    val normals = ArrayList<Offset>(numNodes)
    val tangents = ArrayList<Offset>(numNodes)

    for (i in 0 until numNodes) {
        val prev = if (i > 0) particles[i - 1] else particles[0]
        val next = if (i < numNodes - 1) particles[i + 1] else particles[numNodes - 1]

        val dx = next.x - prev.x
        val dy = next.y - prev.y
        val len = max(0.001f, hypot(dx, dy))
        val tx = dx / len
        val ty = dy / len
        tangents.add(Offset(tx, ty))
        // Perpendicular normal: (-ty, tx)
        normals.add(Offset(-ty, tx))
    }

    // Build ribbon contour points
    val leftPoints = ArrayList<Offset>(numNodes)
    val rightPoints = ArrayList<Offset>(numNodes)

    for (i in 0 until numNodes) {
        val p = particles[i]
        val norm = normals[i]
        leftPoints.add(Offset(p.x + (norm.x * halfWidth), p.y + (norm.y * halfWidth)))
        rightPoints.add(Offset(p.x - (norm.x * halfWidth), p.y - (norm.y * halfWidth)))
    }

    // Smooth ribbon path using quadratic curves through midpoints
    val beltPath = Path().apply {
        moveTo(leftPoints[0].x, leftPoints[0].y)
        for (i in 0 until numNodes - 1) {
            val pCurr = leftPoints[i]
            val pNext = leftPoints[i + 1]
            val midX = (pCurr.x + pNext.x) / 2f
            val midY = (pCurr.y + pNext.y) / 2f
            quadraticTo(pCurr.x, pCurr.y, midX, midY)
        }
        lineTo(leftPoints.last().x, leftPoints.last().y)

        // Flat, clean rectangular bottom hem (authentic martial arts finish)
        lineTo(rightPoints.last().x, rightPoints.last().y)

        for (i in (numNodes - 1) downTo 1) {
            val pCurr = rightPoints[i]
            val pPrev = rightPoints[i - 1]
            val midX = (pCurr.x + pPrev.x) / 2f
            val midY = (pCurr.y + pPrev.y) / 2f
            quadraticTo(pCurr.x, pCurr.y, midX, midY)
        }
        lineTo(rightPoints[0].x, rightPoints[0].y)
        close()
    }

    // Drop shadow
    translate(left = 2f, top = 3f) {
        drawPath(
            path = beltPath,
            color = Color.Black.copy(alpha = 0.16f)
        )
    }

    // Base belt fabric fill
    drawPath(path = beltPath, color = baseColor)

    // Subtle fabric shading gradient
    drawPath(
        path = beltPath,
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Black.copy(alpha = 0.08f),
                Color.White.copy(alpha = 0.12f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.14f)
            ),
            startX = particles[0].x - halfWidth,
            endX = particles[0].x + halfWidth
        )
    )

    // 4 Longitudinal stitch lines along the ribbon length
    val stitchFactors = listOf(-0.55f, -0.18f, 0.18f, 0.55f)
    for (factor in stitchFactors) {
        val stitchPath = Path().apply {
            val startP = particles[0]
            val startNorm = normals[0]
            moveTo(startP.x + (startNorm.x * halfWidth * factor), startP.y + (startNorm.y * halfWidth * factor))
            for (i in 0 until numNodes - 1) {
                val pCurr = particles[i]
                val normCurr = normals[i]
                val pNext = particles[i + 1]
                val normNext = normals[i + 1]
                val ptCurr = Offset(pCurr.x + (normCurr.x * halfWidth * factor), pCurr.y + (normCurr.y * halfWidth * factor))
                val ptNext = Offset(pNext.x + (normNext.x * halfWidth * factor), pNext.y + (normNext.y * halfWidth * factor))
                val midX = (ptCurr.x + ptNext.x) / 2f
                val midY = (ptCurr.y + ptNext.y) / 2f
                quadraticTo(ptCurr.x, ptCurr.y, midX, midY)
            }
            val lastP = particles.last()
            val lastNorm = normals.last()
            lineTo(lastP.x + (lastNorm.x * halfWidth * factor), lastP.y + (lastNorm.y * halfWidth * factor))
        }
        drawPath(
            path = stitchPath,
            color = stitchColor,
            style = Stroke(width = 1.0.dp.toPx(), cap = StrokeCap.Round)
        )
    }

    // Flat bottom hem stitch line (parallel to straight bottom edge, 3.5dp above)
    val tipParticle = particles.last()
    val tipTangent = tangents.last()
    val tipNorm = normals.last()
    val hemOffsetPx = 3.5.dp.toPx()
    val hemCenterX = tipParticle.x - (tipTangent.x * hemOffsetPx)
    val hemCenterY = tipParticle.y - (tipTangent.y * hemOffsetPx)
    drawLine(
        color = stitchColor,
        start = Offset(hemCenterX + (tipNorm.x * halfWidth * 0.9f), hemCenterY + (tipNorm.y * halfWidth * 0.9f)),
        end = Offset(hemCenterX - (tipNorm.x * halfWidth * 0.9f), hemCenterY - (tipNorm.y * halfWidth * 0.9f)),
        strokeWidth = 1.1.dp.toPx()
    )

    // Single-Tail Rank Tab (Only drawn on the right tail, like real Taekwondo belts)
    if (hasRankTab) {
        val stripeNodeIdx = 4.coerceIn(1, numNodes - 2)
        val pA = particles[stripeNodeIdx]
        val pB = particles[stripeNodeIdx + 1]
        val normA = normals[stripeNodeIdx]
        val normB = normals[stripeNodeIdx + 1]

        if (isStripeBelt) {
            // Rank color tab stripe tape (e.g. Yellow on White, Green on Yellow, etc.)
            val stripePath = Path().apply {
                moveTo(pA.x + (normA.x * halfWidth), pA.y + (normA.y * halfWidth))
                lineTo(pB.x + (normB.x * halfWidth), pB.y + (normB.y * halfWidth))
                lineTo(pB.x - (normB.x * halfWidth), pB.y - (normB.y * halfWidth))
                lineTo(pA.x - (normA.x * halfWidth), pA.y - (normA.y * halfWidth))
                close()
            }
            drawPath(path = stripePath, color = accentColor)
            // Stripe border seams
            drawLine(
                color = Color.Black.copy(alpha = 0.22f),
                start = Offset(pA.x + (normA.x * halfWidth), pA.y + (normA.y * halfWidth)),
                end = Offset(pA.x - (normA.x * halfWidth), pA.y - (normA.y * halfWidth)),
                strokeWidth = 1.1.dp.toPx()
            )
            drawLine(
                color = Color.Black.copy(alpha = 0.22f),
                start = Offset(pB.x + (normB.x * halfWidth), pB.y + (normB.y * halfWidth)),
                end = Offset(pB.x - (normB.x * halfWidth), pB.y - (normB.y * halfWidth)),
                strokeWidth = 1.1.dp.toPx()
            )
        } else if (isBlackBelt) {
            // 1st Dan Gold Embroidered Bar (1단)
            val goldColor = Color(0xFFFFD700)
            val barPath = Path().apply {
                val pad = halfWidth * 0.15f
                moveTo(pA.x + (normA.x * (halfWidth - pad)), pA.y + (normA.y * (halfWidth - pad)))
                lineTo(pB.x + (normB.x * (halfWidth - pad)), pB.y + (normB.y * (halfWidth - pad)))
                lineTo(pB.x - (normB.x * (halfWidth - pad)), pB.y - (normB.y * (halfWidth - pad)))
                lineTo(pA.x - (normA.x * (halfWidth - pad)), pA.y - (normA.y * (halfWidth - pad)))
                close()
            }
            drawPath(path = barPath, color = goldColor)
        } else {
            // Authentic woven Kukkiwon rank/federation tag on solid belts
            val patchPath = Path().apply {
                val pad = halfWidth * 0.18f
                moveTo(pA.x + (normA.x * (halfWidth - pad)), pA.y + (normA.y * (halfWidth - pad)))
                lineTo(pB.x + (normB.x * (halfWidth - pad)), pB.y + (normB.y * (halfWidth - pad)))
                lineTo(pB.x - (normB.x * (halfWidth - pad)), pB.y - (normB.y * (halfWidth - pad)))
                lineTo(pA.x - (normA.x * (halfWidth - pad)), pA.y - (normA.y * (halfWidth - pad)))
                close()
            }
            drawPath(path = patchPath, color = Color.Black.copy(alpha = 0.26f))
        }
    }
}

/**
 * Draws the traditional tied square knot at the top.
 */
private fun DrawScope.drawBeltKnot(
    knotLeft: Float,
    knotWidth: Float,
    knotHeight: Float,
    baseColor: Color,
    accentColor: Color,
    isStripeBelt: Boolean
) {
    // Knot drop shadow
    drawRoundRect(
        color = Color.Black.copy(alpha = 0.22f),
        topLeft = Offset(knotLeft + 2.dp.toPx(), 4.dp.toPx()),
        size = Size(knotWidth, knotHeight),
        cornerRadius = CornerRadius(6.dp.toPx())
    )

    // Knot body
    drawRoundRect(
        color = baseColor,
        topLeft = Offset(knotLeft, 2.dp.toPx()),
        size = Size(knotWidth, knotHeight),
        cornerRadius = CornerRadius(6.dp.toPx())
    )

    // Shading highlight
    drawRoundRect(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Black.copy(alpha = 0.20f),
                Color.White.copy(alpha = 0.18f),
                Color.Black.copy(alpha = 0.20f)
            ),
            startX = knotLeft,
            endX = knotLeft + knotWidth
        ),
        topLeft = Offset(knotLeft, 2.dp.toPx()),
        size = Size(knotWidth, knotHeight),
        cornerRadius = CornerRadius(6.dp.toPx())
    )

    // Center wrap tie
    val tieWidth = knotWidth * 0.30f
    val tieLeft = knotLeft + (knotWidth - tieWidth) / 2f
    drawRoundRect(
        color = if (isStripeBelt) accentColor else baseColor,
        topLeft = Offset(tieLeft, 0.dp.toPx()),
        size = Size(tieWidth, knotHeight + 3.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    drawRoundRect(
        color = Color.Black.copy(alpha = 0.18f),
        topLeft = Offset(tieLeft, 0.dp.toPx()),
        size = Size(tieWidth, knotHeight + 3.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = Stroke(width = 1.1.dp.toPx())
    )
}

private fun getStitchColor(belt: BeltRank): Color {
    return when {
        belt == BeltRank.BLACK -> Color(0xFF2C2C2C)
        belt == BeltRank.WHITE -> Color(0xFFD6D6D6)
        else -> Color.Black.copy(alpha = 0.16f)
    }
}
