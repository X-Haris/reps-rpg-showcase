// Excerpt from RepsRPG — the floating "+reps" popup.
// A self-contained Jetpack Compose animation: the number drifts upward,
// scales, and fades out each time the rep count changes. Pure UI, no app state.

package com.abduloski.repsrpg.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Floating rep number that floats up and fades out.
 * Shows "+X" when reps are counted.
 */
@Composable
fun FloatingRepNumber(
    reps: Int,
    modifier: Modifier = Modifier
) {
    var animationState by remember { mutableIntStateOf(0) }

    val offsetY by animateFloatAsState(
        targetValue = when (animationState) {
            0 -> 0f
            else -> -150f
        },
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "offsetY"
    )

    val alpha by animateFloatAsState(
        targetValue = when (animationState) {
            0 -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 800),
        label = "alpha"
    )

    val scale by animateFloatAsState(
        targetValue = when (animationState) {
            0 -> 1.2f
            else -> 0.8f
        },
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )

    LaunchedEffect(reps) {
        animationState = 0
        delay(50)
        animationState = 1
    }

    Text(
        text = "+$reps",
        color = Color(0xFFFFC54D),  // NeonYellow
        fontSize = 56.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = modifier
            .graphicsLayer {
                translationY = offsetY
                scaleX = scale
                scaleY = scale
            }
            .alpha(alpha)
    )
}
