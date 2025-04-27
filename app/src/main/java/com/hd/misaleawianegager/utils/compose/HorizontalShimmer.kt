package com.hd.misaleawianegager.utils.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalShimmer(
    modifier: Modifier = Modifier,
    gradientWidth: Float = 0.2f  // Width of the shimmer gradient (relative to total width)
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = "shimmerAnimation"
    )

    Box(modifier = modifier) {
        val density = LocalDensity.current
        var size by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    size = coordinates.size
                }
                .background(
                    brush = remember(size) {
                        if (size.width > 0 && size.height > 0) {
                            Brush.linearGradient(
                                colors = shimmerColors,
                                start = Offset(
                                    x = translateAnim.value * size.width - (size.width * gradientWidth),
                                    y = 0f
                                ),
                                end = Offset(
                                    x = translateAnim.value * size.width,
                                    y = size.height.toFloat()
                                )
                            )
                        } else {
                            Brush.linearGradient(shimmerColors)
                        }
                    }
                )
        )
    }
}