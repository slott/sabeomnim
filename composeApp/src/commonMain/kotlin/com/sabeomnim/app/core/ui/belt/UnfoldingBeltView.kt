package com.sabeomnim.app.core.ui.belt

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.data.models.BeltRank
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Authentic Taekwondo Martial Arts Belt (Tti) with dynamic unfolding physics animation.
 * Features 6 longitudinal stitching lines, rank stripes, gold embroidery for Black Belt,
 * and an angled martial arts belt tail.
 */
@Composable
fun UnfoldingBeltView(
    belt: BeltRank,
    modifier: Modifier = Modifier,
    beltWidth: Dp = 38.dp,
    maxBeltLength: Dp = 190.dp,
    autoPlay: Boolean = true
) {
    val coroutineScope = rememberCoroutineScope()
    val unfoldProgress = remember { Animatable(0f) }

    val beltAnimationSpec = remember {
        tween<Float>(
            durationMillis = 1350,
            easing = FastOutSlowInEasing
        )
    }

    // Re-trigger the unfolding animation whenever the selected belt changes
    LaunchedEffect(belt) {
        if (autoPlay) {
            unfoldProgress.snapTo(0f)
            unfoldProgress.animateTo(
                targetValue = 1f,
                animationSpec = beltAnimationSpec
            )
        } else {
            unfoldProgress.snapTo(1f)
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .width(beltWidth + 24.dp)
            .height(maxBeltLength + 30.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                coroutineScope.launch {
                    unfoldProgress.snapTo(0f)
                    unfoldProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = beltAnimationSpec
                    )
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        val baseBeltColor = Color(belt.colorHex)
        val accentBeltColor = Color(belt.accentColorHex)
        val isStripeBelt = belt.name.contains("STRIPE")
        val isBlackBelt = belt == BeltRank.BLACK

        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = unfoldProgress.value
            if (progress <= 0.001f) return@Canvas

            val bWidthPx = beltWidth.toPx()
            val totalMaxLenPx = maxBeltLength.toPx()
            val currentLenPx = totalMaxLenPx * progress

            // Realistic cloth pendulum sway when falling
            val swayAngle = (1f - progress) * sin(progress * 3.5f * PI.toFloat()) * 9f

            val knotWidth = bWidthPx * 1.25f
            val knotHeight = 22.dp.toPx()
            val knotLeft = (size.width - knotWidth) / 2f
            val beltLeft = (size.width - bWidthPx) / 2f

            // 1. Draw Belt Hanging & Unfolding
            rotate(degrees = swayAngle, pivot = Offset(size.width / 2f, knotHeight / 2f)) {
                // Belt Tail Path with authentic 45-degree angled martial arts tip
                val beltPath = Path().apply {
                    moveTo(beltLeft, knotHeight * 0.7f)
                    lineTo(beltLeft + bWidthPx, knotHeight * 0.7f)
                    
                    if (progress >= 0.85f) {
                        // Fully unrolled angled tip
                        val tailSlope = bWidthPx * 0.45f
                        lineTo(beltLeft + bWidthPx, currentLenPx - tailSlope)
                        lineTo(beltLeft, currentLenPx)
                    } else {
                        // During active roll/unfold, bottom appears rolled
                        lineTo(beltLeft + bWidthPx, currentLenPx)
                        lineTo(beltLeft, currentLenPx)
                    }
                    close()
                }

                // Drop shadow
                translate(left = 4f, top = 6f) {
                    drawPath(
                        path = beltPath,
                        color = Color.Black.copy(alpha = 0.18f * progress)
                    )
                }

                // Main Belt Body
                drawPath(
                    path = beltPath,
                    color = baseBeltColor
                )

                // Belt Woven Fabric Shading (subtle gradient across width)
                val beltHighlightBrush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.12f),
                        Color.White.copy(alpha = 0.15f),
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.18f)
                    ),
                    startX = beltLeft,
                    endX = beltLeft + bWidthPx
                )
                drawPath(
                    path = beltPath,
                    brush = beltHighlightBrush
                )

                // 2. Six Longitudinal Stitching Lines (Authentic Kukkiwon Belt feature)
                val stitchColor = if (isBlackBelt) {
                    Color(0xFF333333)
                } else if (belt == BeltRank.WHITE) {
                    Color(0xFFDDDDDD)
                } else {
                    Color.Black.copy(alpha = 0.16f)
                }

                val stitchCols = 6
                for (i in 1..stitchCols) {
                    val x = beltLeft + (bWidthPx * (i.toFloat() / (stitchCols + 1)))
                    val endY = if (progress >= 0.85f) currentLenPx - (bWidthPx * 0.25f) else currentLenPx
                    drawLine(
                        color = stitchColor,
                        start = Offset(x, knotHeight * 0.8f),
                        end = Offset(x, endY),
                        strokeWidth = 1.2.dp.toPx()
                    )
                }

                // 3. Center Rank Stripe (for 9th, 7th, 5th, 3rd, 1st Geup)
                if (isStripeBelt) {
                    val stripeWidth = bWidthPx * 0.28f
                    val stripeLeft = beltLeft + (bWidthPx - stripeWidth) / 2f
                    val stripeEndY = if (progress >= 0.85f) currentLenPx - (bWidthPx * 0.35f) else currentLenPx

                    drawRect(
                        color = accentBeltColor,
                        topLeft = Offset(stripeLeft, knotHeight * 0.8f),
                        size = Size(stripeWidth, (stripeEndY - knotHeight * 0.8f).coerceAtLeast(0f))
                    )

                    // Subtle bevel on stripe
                    drawLine(
                        color = Color.Black.copy(alpha = 0.2f),
                        start = Offset(stripeLeft, knotHeight * 0.8f),
                        end = Offset(stripeLeft, stripeEndY),
                        strokeWidth = 1f
                    )
                    drawLine(
                        color = Color.Black.copy(alpha = 0.2f),
                        start = Offset(stripeLeft + stripeWidth, knotHeight * 0.8f),
                        end = Offset(stripeLeft + stripeWidth, stripeEndY),
                        strokeWidth = 1f
                    )
                }

                // 4. Black Belt Gold Embroidery (1st Dan / Il Dan)
                if (isBlackBelt && progress >= 0.8f) {
                    val goldColor = Color(0xFFFFD700)
                    val danBarY = currentLenPx - 42.dp.toPx()
                    val danBarHeight = 7.dp.toPx()
                    val danBarPadding = 5.dp.toPx()

                    // Gold Dan Bar 1 (1단)
                    drawRoundRect(
                        color = goldColor,
                        topLeft = Offset(beltLeft + danBarPadding, danBarY),
                        size = Size(bWidthPx - (danBarPadding * 2), danBarHeight),
                        cornerRadius = CornerRadius(2.dp.toPx())
                    )

                    // Gold Embroidered Rank Indicator Tip
                    val patchY = danBarY - 26.dp.toPx()
                    val patchHeight = 20.dp.toPx()
                    drawRoundRect(
                        color = Color(0xFF151515),
                        topLeft = Offset(beltLeft + danBarPadding, patchY),
                        size = Size(bWidthPx - (danBarPadding * 2), patchHeight),
                        cornerRadius = CornerRadius(2.dp.toPx())
                    )
                    // Gold border on patch
                    drawRoundRect(
                        color = goldColor.copy(alpha = 0.8f),
                        topLeft = Offset(beltLeft + danBarPadding, patchY),
                        size = Size(bWidthPx - (danBarPadding * 2), patchHeight),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2.dp.toPx())
                    )
                } else if (!isBlackBelt && progress >= 0.8f) {
                    // Geup rank tag near tip
                    val tagY = currentLenPx - 34.dp.toPx()
                    val tagHeight = 16.dp.toPx()
                    val tagPadding = 6.dp.toPx()

                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.25f),
                        topLeft = Offset(beltLeft + tagPadding, tagY),
                        size = Size(bWidthPx - (tagPadding * 2), tagHeight),
                        cornerRadius = CornerRadius(3.dp.toPx())
                    )
                }

                // 5. Unrolling Curl / Roll Effect at the bottom while in motion
                if (progress in 0.05f..0.85f) {
                    val curlHeight = 14.dp.toPx()
                    val rollColor = baseBeltColor.copy(alpha = 0.95f)

                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                rollColor,
                                Color.Black.copy(alpha = 0.4f)
                            ),
                            startY = currentLenPx - curlHeight,
                            endY = currentLenPx
                        ),
                        topLeft = Offset(beltLeft - 2.dp.toPx(), currentLenPx - curlHeight),
                        size = Size(bWidthPx + 4.dp.toPx(), curlHeight),
                        cornerRadius = CornerRadius(6.dp.toPx())
                    )
                }
            }

            // 6. Traditional Belt Knot at the Top (Anchor)
            drawRoundRect(
                color = Color.Black.copy(alpha = 0.22f),
                topLeft = Offset(knotLeft + 2.dp.toPx(), 4.dp.toPx()),
                size = Size(knotWidth, knotHeight),
                cornerRadius = CornerRadius(6.dp.toPx())
            )

            drawRoundRect(
                color = baseBeltColor,
                topLeft = Offset(knotLeft, 2.dp.toPx()),
                size = Size(knotWidth, knotHeight),
                cornerRadius = CornerRadius(6.dp.toPx())
            )

            // Knot shading and center wrap
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.25f),
                        Color.White.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.25f)
                    ),
                    startX = knotLeft,
                    endX = knotLeft + knotWidth
                ),
                topLeft = Offset(knotLeft, 2.dp.toPx()),
                size = Size(knotWidth, knotHeight),
                cornerRadius = CornerRadius(6.dp.toPx())
            )

            // Center Knot Tie Wrap
            val tieWidth = knotWidth * 0.38f
            val tieLeft = (size.width - tieWidth) / 2f
            drawRoundRect(
                color = if (isStripeBelt) accentBeltColor else baseBeltColor,
                topLeft = Offset(tieLeft, 0.dp.toPx()),
                size = Size(tieWidth, knotHeight + 3.dp.toPx()),
                cornerRadius = CornerRadius(4.dp.toPx())
            )

            drawRoundRect(
                color = Color.Black.copy(alpha = 0.2f),
                topLeft = Offset(tieLeft, 0.dp.toPx()),
                size = Size(tieWidth, knotHeight + 3.dp.toPx()),
                cornerRadius = CornerRadius(4.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2.dp.toPx())
            )
        }
    }
}
