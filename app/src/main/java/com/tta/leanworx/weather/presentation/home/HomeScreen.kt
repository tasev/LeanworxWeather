package com.tta.leanworx.weather.presentation.home

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tta.leanworx.weather.R
import com.tta.leanworx.weather.presentation.components.CustomAlertDialog
import com.tta.leanworx.weather.presentation.components.HomeTopAppBar
import com.tta.leanworx.weather.presentation.states.UIState
import com.tta.leanworx.weather.presentation.states.SearchComponentState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val searchWidgetState by  rememberSaveable {viewModel.searchComponentState}
    val searchTextState by  rememberSaveable {viewModel.searchTextState}
    val uiState: UIState by viewModel.uiState.collectAsStateWithLifecycle()


    val lifecycleOwner = LocalLifecycleOwner.current
    val state = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    state.launchMultiplePermissionRequest()
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    PermissionsRequired(
        multiplePermissionsState = state,
        permissionsNotGrantedContent = {
            CustomAlertDialog()
        },
        permissionsNotAvailableContent = {
            CustomAlertDialog()
        }
    ) {
        LaunchedEffect(key1 = Unit) {
            if(uiState.currentWeather.userLocation.city?.isBlank() == true && uiState.error.isBlank())
            viewModel.getForecast()
        }
        Scaffold(
            topBar = {
                HomeTopAppBar(
                    uiState = uiState,
                    searchWidgetState = searchWidgetState,
                    searchTextState = searchTextState,
                    onTextChange = { viewModel.updateSearchTextState(it) },
                    onCloseClicked = { viewModel.updateSearchComponentState(SearchComponentState.CLOSED) },
                    onSearchClicked = {
                        viewModel.getForecast(it)
                        viewModel.updateSearchComponentState(SearchComponentState.CLOSED)
                    },
                    onSearchTriggered = {
                        viewModel.updateSearchComponentState(newValue = SearchComponentState.OPENED)
                    })
            },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        uiState.isLoading -> {
                            HomeLoadingAnimation(
                                modifier = Modifier.fillMaxSize(),
                                animation = R.raw.animation_loading
                            )
                        }

                        uiState.error.isNotEmpty() -> {
                            HomeErrorScreen(uiState = uiState, viewModel = viewModel)
                        }

                        else -> {
                            HomeSuccessScreen(
                                uiState = uiState,
                                modifier = modifier
                            )
                        }
                    }
                }
            },
        )
    }
}