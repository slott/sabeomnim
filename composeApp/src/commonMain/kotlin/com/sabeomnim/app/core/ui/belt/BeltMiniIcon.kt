package com.sabeomnim.app.core.ui.belt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sabeomnim.app.data.models.BeltRank

@Composable
fun BeltMiniIcon(
    belt: BeltRank,
    modifier: Modifier = Modifier,
    width: Dp = 18.dp,
    height: Dp = 10.dp
) {
    val baseColor = Color(belt.colorHex)
    val stripeColor = Color(belt.stripeColorHex ?: belt.accentColorHex)
    val isWhite = belt == BeltRank.WHITE
    val isBlack = belt == BeltRank.BLACK

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(2.dp))
            .background(baseColor)
            .then(
                if (isWhite) {
                    Modifier.border(0.8.dp, Color.LightGray, RoundedCornerShape(2.dp))
                } else {
                    Modifier
                }
            )
    ) {
        if (belt.stripeCount > 0 || isBlack) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stripeWidthPx = 1.6.dp.toPx()
                if (belt.stripeCount > 0) {
                    // Draw 1, 2, or 3 vertical stripes from right edge
                    val startOffsetFromRight = 3.5.dp.toPx()
                    val gapBetweenStripes = 2.4.dp.toPx()
                    for (i in 0 until belt.stripeCount) {
                        val x = size.width - startOffsetFromRight - (i * gapBetweenStripes)
                        drawLine(
                            color = stripeColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = stripeWidthPx
                        )
                    }
                } else if (isBlack) {
                    // Gold 1st Dan stripe
                    val x = size.width - 3.5.dp.toPx()
                    drawLine(
                        color = Color(0xFFFFD700),
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = stripeWidthPx
                    )
                }
            }
        }
    }
}
