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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
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

    val isDragged: Boolean
        get() = draggedTail != null

    fun hasKineticEnergy(): Boolean {
        var maxV2 = 0f
        for (tail in listOf(leftTail, rightTail)) {
            for (i in 1 until tail.particles.size) {
                val p = tail.particles[i]
                val dx = p.x - p.prevX
                val dy = p.y - p.prevY
                val v2 = dx * dx + dy * dy
                if (v2 > maxV2) maxV2 = v2
            }
        }
        return maxV2 > (0.05f * density * density)
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
    boxWidth: Dp = 96.dp,
    maxBeltLength: Dp = 175.dp,
    autoPlay: Boolean = true,
    onInteraction: () -> Unit = {}
) {
    val density = LocalDensity.current.density
    val unfoldProgress = remember(belt, autoPlay) { Animatable(if (autoPlay) 0.10f else 1f) }

    val boxHeight = maxBeltLength + 32.dp

    val boxWidthPx = boxWidth.value * density
    val totalHeightPx = boxHeight.value * density
    val tailWidthPx = 18.5f * density
    val knotCenterXPx = boxWidthPx / 2f
    val knotAnchorY = 17.dp.value * density
    val fullTailLenPx = maxBeltLength.value * density

    // Create and remember the 2D physics system
    val physics = remember(belt) {
        BeltPhysicsSystem(
            density = density,
            boxWidthPx = boxWidthPx,
            totalHeightPx = totalHeightPx,
            tailWidthPx = tailWidthPx
        ).apply {
            initialize(knotCenterXPx, knotAnchorY, fullTailLenPx)
            if (!autoPlay) {
                onUnfoldProgress(1f, fullTailLenPx)
            }
        }
    }

    var isSimulating by remember(belt, autoPlay) { mutableStateOf(autoPlay) }
    var frameTick by remember { mutableStateOf(0L) }

    // Trigger unfolding animation when belt changes or requested
    LaunchedEffect(belt, autoPlay) {
        if (autoPlay) {
            physics.initialize(knotCenterXPx, knotAnchorY, fullTailLenPx)
            isSimulating = true
            unfoldProgress.snapTo(0.10f)
            unfoldProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1100, easing = FastOutSlowInEasing)
            )
        } else {
            physics.initialize(knotCenterXPx, knotAnchorY, fullTailLenPx)
            physics.onUnfoldProgress(1f, fullTailLenPx)
            unfoldProgress.snapTo(1f)
            isSimulating = false
        }
    }

    // Real-time physics simulation loop: runs at VSync rate, sleeps when belt is at rest
    LaunchedEffect(isSimulating) {
        if (!isSimulating) return@LaunchedEffect
        var lastNanos = withFrameNanos { it }
        var restFrames = 0
        while (isActive && isSimulating) {
            withFrameNanos { timeNanos ->
                val dt = ((timeNanos - lastNanos) / 1_000_000_000f).coerceIn(0.005f, 0.033f)
                lastNanos = timeNanos
                physics.onUnfoldProgress(unfoldProgress.value, fullTailLenPx)
                physics.update(dt)
                frameTick = timeNanos

                // Automatically sleep after settling
                if (unfoldProgress.value >= 1f && !physics.isDragged && !physics.hasKineticEnergy()) {
                    restFrames++
                    if (restFrames > 25) {
                        isSimulating = false
                    }
                } else {
                    restFrames = 0
                }
            }
        }
    }

    val baseBeltColor = Color(belt.colorHex)
    val accentBeltColor = Color(belt.accentColorHex)
    val stripeColor = Color(belt.stripeColorHex ?: belt.accentColorHex)
    val stripeCount = belt.stripeCount
    val isBlackBelt = belt == BeltRank.BLACK

    Box(
        modifier = modifier
            .width(boxWidth)
            .height(boxHeight)
            .pointerInput(belt) {
                detectTapGestures { offset ->
                    onInteraction()
                    physics.onTap(offset.x, offset.y)
                    isSimulating = true
                }
            }
            .pointerInput(belt) {
                detectDragGestures(
                    onDragStart = { offset ->
                        onInteraction()
                        if (physics.onDragStart(offset.x, offset.y)) {
                            isSimulating = true
                        }
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        physics.onDrag(change.position.x, change.position.y)
                        isSimulating = true
                    },
                    onDragEnd = {
                        physics.onDragEnd()
                        isSimulating = true
                    },
                    onDragCancel = {
                        physics.onDragEnd()
                        isSimulating = true
                    }
                )
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (frameTick == -1L) return@Canvas

            // 1. Draw Left Tail (Plain cotton tail, no rank tab)
            drawBeltTail(
                tail = physics.leftTail,
                tailWidthPx = tailWidthPx,
                baseColor = baseBeltColor,
                stripeColor = stripeColor,
                stitchColor = getStitchColor(belt),
                hasRankTab = false,
                stripeCount = 0,
                isBlackBelt = false
            )

            // 2. Draw Right Tail (Front tail with authentic color tab / Dan bar)
            drawBeltTail(
                tail = physics.rightTail,
                tailWidthPx = tailWidthPx,
                baseColor = baseBeltColor,
                stripeColor = stripeColor,
                stitchColor = getStitchColor(belt),
                hasRankTab = true,
                stripeCount = stripeCount,
                isBlackBelt = isBlackBelt
            )

            // 3. Draw Belt Knot & Waist Band at top (Wide martial arts waist wrap anchoring both tails)
            drawBeltWaistAndKnot(
                centerX = size.width / 2f,
                waistWidth = 88.dp.toPx(),
                waistHeight = 17.5.dp.toPx(),
                waistTop = 5.dp.toPx(),
                knotWrapWidth = 26.dp.toPx(),
                knotWrapHeight = 23.5.dp.toPx(),
                knotWrapTop = 2.dp.toPx(),
                baseColor = baseBeltColor,
                stitchColor = getStitchColor(belt)
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
    stripeColor: Color,
    stitchColor: Color,
    hasRankTab: Boolean,
    stripeCount: Int,
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

    // Helper to build smooth longitudinal spline curves that bend with the belt ribbon
    fun buildRibbonSplinePath(frac: Float): Path {
        val path = Path()
        val pts = ArrayList<Offset>(numNodes)
        for (i in 0 until numNodes) {
            val p = particles[i]
            val norm = normals[i]
            pts.add(Offset(p.x + (norm.x * halfWidth * frac), p.y + (norm.y * halfWidth * frac)))
        }
        path.moveTo(pts[0].x, pts[0].y)
        for (i in 0 until numNodes - 1) {
            val pCurr = pts[i]
            val pNext = pts[i + 1]
            val midX = (pCurr.x + pNext.x) / 2f
            val midY = (pCurr.y + pNext.y) / 2f
            path.quadraticTo(pCurr.x, pCurr.y, midX, midY)
        }
        path.lineTo(pts.last().x, pts.last().y)
        return path
    }

    // Dynamic 3D fabric shading & sheen that bends and follows the moving ribbon perfectly
    clipPath(beltPath) {
        // 1. Left edge cylindrical shadow (bevel)
        drawPath(
            path = buildRibbonSplinePath(0.92f),
            color = Color.Black.copy(alpha = 0.10f),
            style = Stroke(width = halfWidth * 0.40f, cap = StrokeCap.Round)
        )

        // 2. Left-inner fabric sheen highlight (woven satin/cotton reflection)
        drawPath(
            path = buildRibbonSplinePath(0.38f),
            color = Color.White.copy(alpha = 0.15f),
            style = Stroke(width = halfWidth * 0.60f, cap = StrokeCap.Round)
        )

        // 3. Central subtle fabric depression between stitch seams
        drawPath(
            path = buildRibbonSplinePath(0f),
            color = Color.Black.copy(alpha = 0.05f),
            style = Stroke(width = halfWidth * 0.30f, cap = StrokeCap.Round)
        )

        // 4. Right edge cylindrical shadow (depth & thickness)
        drawPath(
            path = buildRibbonSplinePath(-0.90f),
            color = Color.Black.copy(alpha = 0.16f),
            style = Stroke(width = halfWidth * 0.45f, cap = StrokeCap.Round)
        )
    }

    // Authentic martial arts belt longitudinal seam stitches (3 parallel running rows)
    val seamOffsets = listOf(-0.55f, 0f, 0.55f)
    for (frac in seamOffsets) {
        val stitchPath = buildRibbonSplinePath(frac)
        drawPath(
            path = stitchPath,
            color = stitchColor,
            style = Stroke(
                width = 1.0.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.5.dp.toPx(), 3.5.dp.toPx()), 0f)
            )
        )
    }

    // Authentic hem border stitch across the rectangular bottom end
    val tipIdx = numNodes - 1
    val tipParticle = particles[tipIdx]
    val tipNorm = normals[tipIdx]
    val tipTangent = tangents[tipIdx]

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
        val prevParticle = particles[tipIdx - 1]

        // Tangent vector pointing from prev particle to tip
        val tanX = tipParticle.x - prevParticle.x
        val tanY = tipParticle.y - prevParticle.y
        val tanLen = kotlin.math.hypot(tanX, tanY).coerceAtLeast(0.001f)
        val unitTanX = tanX / tanLen
        val unitTanY = tanY / tanLen

        // Normal vector at the lower tail segment
        val norm = normals[tipIdx - 1]

        if (stripeCount > 0) {
            // Rank color tab stripe tape (e.g. Red on Blue, Black on Red, etc.)
            // Thin tape like real life: ~5.6dp thick along the belt length
            val stripeHalfThick = 2.8.dp.toPx()

            // Calculate stripe positions based on stripeCount (1, 2, or 3 stripes)
            val stripeOffsets = when (stripeCount) {
                1 -> listOf(28.dp.toPx())
                2 -> listOf(23.dp.toPx(), 34.dp.toPx())
                3 -> listOf(18.dp.toPx(), 28.dp.toPx(), 38.dp.toPx())
                else -> (0 until stripeCount).map { i -> (18 + i * 10).dp.toPx() }
            }

            for (distanceFromTip in stripeOffsets) {
                val stripeCenterX = tipParticle.x - (unitTanX * distanceFromTip)
                val stripeCenterY = tipParticle.y - (unitTanY * distanceFromTip)

                val pTopX = stripeCenterX - (unitTanX * stripeHalfThick)
                val pTopY = stripeCenterY - (unitTanY * stripeHalfThick)
                val pBotX = stripeCenterX + (unitTanX * stripeHalfThick)
                val pBotY = stripeCenterY + (unitTanY * stripeHalfThick)

                val stripePath = Path().apply {
                    moveTo(pTopX + (norm.x * halfWidth), pTopY + (norm.y * halfWidth))
                    lineTo(pBotX + (norm.x * halfWidth), pBotY + (norm.y * halfWidth))
                    lineTo(pBotX - (norm.x * halfWidth), pBotY - (norm.y * halfWidth))
                    lineTo(pTopX - (norm.x * halfWidth), pTopY - (norm.y * halfWidth))
                    close()
                }
                drawPath(path = stripePath, color = stripeColor)

                // Subtle tape edge seams
                drawLine(
                    color = Color.Black.copy(alpha = 0.28f),
                    start = Offset(pTopX + (norm.x * halfWidth), pTopY + (norm.y * halfWidth)),
                    end = Offset(pTopX - (norm.x * halfWidth), pTopY - (norm.y * halfWidth)),
                    strokeWidth = 0.8.dp.toPx()
                )
                drawLine(
                    color = Color.Black.copy(alpha = 0.28f),
                    start = Offset(pBotX + (norm.x * halfWidth), pBotY + (norm.y * halfWidth)),
                    end = Offset(pBotX - (norm.x * halfWidth), pBotY - (norm.y * halfWidth)),
                    strokeWidth = 0.8.dp.toPx()
                )
            }
        } else if (isBlackBelt) {
            // 1st Dan Gold Embroidered Bar (1단) - thin, elegant 5dp gold bar
            val distanceFromTip = 28.dp.toPx()
            val stripeCenterX = tipParticle.x - (unitTanX * distanceFromTip)
            val stripeCenterY = tipParticle.y - (unitTanY * distanceFromTip)

            val goldColor = Color(0xFFFFD700)
            val barHalfThick = 2.5.dp.toPx()
            val pTopX = stripeCenterX - (unitTanX * barHalfThick)
            val pTopY = stripeCenterY - (unitTanY * barHalfThick)
            val pBotX = stripeCenterX + (unitTanX * barHalfThick)
            val pBotY = stripeCenterY + (unitTanY * barHalfThick)
            val pad = halfWidth * 0.18f

            val barPath = Path().apply {
                moveTo(pTopX + (norm.x * (halfWidth - pad)), pTopY + (norm.y * (halfWidth - pad)))
                lineTo(pBotX + (norm.x * (halfWidth - pad)), pBotY + (norm.y * (halfWidth - pad)))
                lineTo(pBotX - (norm.x * (halfWidth - pad)), pBotY - (norm.y * (halfWidth - pad)))
                lineTo(pTopX - (norm.x * (halfWidth - pad)), pTopY - (norm.y * (halfWidth - pad)))
                close()
            }
            drawPath(path = barPath, color = goldColor)
        }
        // NOTE: For solid belts without stripes (stripeCount == 0 && !isBlackBelt),
        // we deliberately do NOT draw any patch or rectangle! Pure, solid belt cloth!
    }
}

/**
 * Draws the wide horizontal waist band and central knot wrap of the tied Taekwondo belt,
 * matching authentic Kukkiwon silhouette with smooth rounded ends and longitudinal stitches.
 */
private fun DrawScope.drawBeltWaistAndKnot(
    centerX: Float,
    waistWidth: Float,
    waistHeight: Float,
    waistTop: Float,
    knotWrapWidth: Float,
    knotWrapHeight: Float,
    knotWrapTop: Float,
    baseColor: Color,
    stitchColor: Color
) {
    val waistLeft = centerX - (waistWidth / 2f)
    val knotWrapLeft = centerX - (knotWrapWidth / 2f)
    val waistRadius = 4.5.dp.toPx()
    val knotRadius = 5.0.dp.toPx()

    // 1. Waist band drop shadow
    drawRoundRect(
        color = Color.Black.copy(alpha = 0.22f),
        topLeft = Offset(waistLeft + 1.5.dp.toPx(), waistTop + 2.5.dp.toPx()),
        size = Size(waistWidth, waistHeight),
        cornerRadius = CornerRadius(waistRadius)
    )

    // 2. Waist band base fabric fill
    drawRoundRect(
        color = baseColor,
        topLeft = Offset(waistLeft, waistTop),
        size = Size(waistWidth, waistHeight),
        cornerRadius = CornerRadius(waistRadius)
    )

    // 3. Waist band fabric shading gradient
    drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.18f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.18f)
            ),
            startY = waistTop,
            endY = waistTop + waistHeight
        ),
        topLeft = Offset(waistLeft, waistTop),
        size = Size(waistWidth, waistHeight),
        cornerRadius = CornerRadius(waistRadius)
    )

    // 4. Waist band longitudinal seam stitches (3 horizontal rows)
    val seamFractions = listOf(-0.52f, 0f, 0.52f)
    val halfWaist = waistHeight / 2f
    for (frac in seamFractions) {
        val sy = waistTop + halfWaist + (halfWaist * frac)
        drawLine(
            color = stitchColor,
            start = Offset(waistLeft + 4.dp.toPx(), sy),
            end = Offset(waistLeft + waistWidth - 4.dp.toPx(), sy),
            strokeWidth = 1.0.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.5.dp.toPx(), 3.5.dp.toPx()), 0f)
        )
    }

    // 5. Center knot wrap drop shadow
    drawRoundRect(
        color = Color.Black.copy(alpha = 0.26f),
        topLeft = Offset(knotWrapLeft + 2.dp.toPx(), knotWrapTop + 2.dp.toPx()),
        size = Size(knotWrapWidth, knotWrapHeight),
        cornerRadius = CornerRadius(knotRadius)
    )

    // 6. Center knot wrap base fabric
    drawRoundRect(
        color = baseColor,
        topLeft = Offset(knotWrapLeft, knotWrapTop),
        size = Size(knotWrapWidth, knotWrapHeight),
        cornerRadius = CornerRadius(knotRadius)
    )

    // 7. Center knot wrap 3D cylinder highlight
    drawRoundRect(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Black.copy(alpha = 0.22f),
                Color.White.copy(alpha = 0.24f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.24f)
            ),
            startX = knotWrapLeft,
            endX = knotWrapLeft + knotWrapWidth
        ),
        topLeft = Offset(knotWrapLeft, knotWrapTop),
        size = Size(knotWrapWidth, knotWrapHeight),
        cornerRadius = CornerRadius(knotRadius)
    )

    // 8. Center knot wrap subtle contour border
    drawRoundRect(
        color = Color.Black.copy(alpha = 0.16f),
        topLeft = Offset(knotWrapLeft, knotWrapTop),
        size = Size(knotWrapWidth, knotWrapHeight),
        cornerRadius = CornerRadius(knotRadius),
        style = Stroke(width = 1.0.dp.toPx())
    )
}

private fun getStitchColor(belt: BeltRank): Color {
    return when {
        belt == BeltRank.BLACK -> Color(0xFF2C2C2C)
        belt == BeltRank.WHITE -> Color(0xFFD6D6D6)
        else -> Color.Black.copy(alpha = 0.16f)
    }
}
