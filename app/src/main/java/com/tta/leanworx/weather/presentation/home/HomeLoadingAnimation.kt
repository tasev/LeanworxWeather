package com.tta.leanworx.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun HomeLoadingAnimation(modifier: Modifier, animation: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 2f,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}