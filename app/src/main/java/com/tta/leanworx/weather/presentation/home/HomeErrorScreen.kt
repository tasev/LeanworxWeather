package com.tta.leanworx.weather.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tta.leanworx.weather.R
import com.tta.leanworx.weather.presentation.states.UIState

@Composable
fun HomeErrorScreen(uiState: UIState, viewModel: HomeViewModel) {
    Column(
        Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = uiState.error,
            style = MaterialTheme.typography.headlineMedium
        )
        HomeLoadingAnimation(
            modifier = Modifier.fillMaxSize(),
            animation = R.raw.animation_error
        )
    }
}