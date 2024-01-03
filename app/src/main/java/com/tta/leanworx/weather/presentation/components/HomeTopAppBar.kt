package com.tta.leanworx.weather.presentation.components

import androidx.compose.runtime.Composable
import com.tta.leanworx.weather.presentation.states.UIState
import com.tta.leanworx.weather.presentation.states.SearchComponentState

@Composable
fun HomeTopAppBar(
    uiState: UIState,
    searchWidgetState: SearchComponentState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchComponentState.CLOSED -> {
            DefaultAppBar(
                uiState = uiState,
                onSearchClicked = onSearchTriggered
            )
        }

        SearchComponentState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}