package com.tta.leanworx.weather.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.leanworx.weather.domain.models.UserLocation
import com.tta.leanworx.weather.domain.models.toUserLocation
import com.tta.leanworx.weather.domain.repository.LocationClient
import com.tta.leanworx.weather.domain.usecases.GetCoordinatesFromNameUseCase
import com.tta.leanworx.weather.domain.usecases.GetCurrentAndForecastsWeatherDataUseCase
import com.tta.leanworx.weather.domain.usecases.GetNameFromCoordinatesUseCase
import com.tta.leanworx.weather.domain.utils.Response
import com.tta.leanworx.weather.presentation.states.CurrentWeather
import com.tta.leanworx.weather.presentation.states.LocationState
import com.tta.leanworx.weather.presentation.states.SearchComponentState
import com.tta.leanworx.weather.presentation.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val defaultLocationManager: LocationClient,
    private val getNameFromCoordinatesUseCase: GetNameFromCoordinatesUseCase,
    private val getCoordinatesFromNameUseCase: GetCoordinatesFromNameUseCase,
    private val getCurrentAndForecastsWeatherDataUseCase: GetCurrentAndForecastsWeatherDataUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> =
        MutableStateFlow(UIState(isLoading = true))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _searchComponentState: MutableState<SearchComponentState> =
        mutableStateOf(value = SearchComponentState.CLOSED)
    val searchComponentState: State<SearchComponentState> = _searchComponentState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchComponentState(newValue: SearchComponentState) {
        _searchComponentState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private val currentLocation = MutableStateFlow(LocationState())

    fun getForecast(city: String = "") {
        CoroutineScope(Dispatchers.Default).launch {
            if (city.isBlank()) {
                getCurrentLocation()
            } else {
                getCoordinatesFromName(city)
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            defaultLocationManager.getLocation().collectLatest { res ->
                when (res) {
                    is Response.Loading ->
                        currentLocation.update {
                            it.copy(
                                isLoading = true,
                                error = ""
                            )
                        }

                    is Response.Success -> {
                        res.data?.let { loc ->
                            val userLoc = loc.toUserLocation()
                            updateUiStateWithCoordinates(userLoc)
                        }
                    }

                    is Response.Error -> {
                        currentLocation.update {
                            it.copy(
                                isLoading = false,
                                error = res.message ?: "Something went wrong."
                            )
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = res.message ?: "Current Location error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateUiStateWithCoordinates(userLocation: UserLocation) {
        viewModelScope.launch {
            currentLocation.update {
                it.copy(
                    location = userLocation,
                    isLoading = false,
                    error = ""
                )
            }

            val coordinate = async {
                getNameFromCoordinates(
                    lat = userLocation.lat.toString(),
                    long = userLocation.long.toString()
                )
            }

            coordinate.await()
        }
    }

    private fun getNameFromCoordinates(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getNameFromCoordinatesUseCase.invoke(lat, long).collectLatest { res ->
                when (res) {
                    is Response.Loading -> currentLocation.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Response.Success -> {
                        res.data?.let { geo ->
                            currentLocation.update {
                                it.copy(
                                    location = it.location.copy(
                                        city = geo.name ?: "",
                                        state = geo.state ?: "",
                                        country = geo.country ?: ""
                                    ),
                                    isLoading = false,
                                    error = ""
                                )
                            }
                            val dailyForecast = async {
                                getCurrentAndForecastsWeatherData(
                                    lat = lat,
                                    long = long
                                )
                            }
                            dailyForecast.await()
                        }
                    }

                    is Response.Error -> {
                        currentLocation.update {
                            it.copy(
                                isLoading = false,
                                error = res.message ?: "Something went wrong."
                            )
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = res.message ?: "Name From Coordinates error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCoordinatesFromName(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCoordinatesFromNameUseCase.invoke(city).collectLatest { res ->
                when (res) {
                    is Response.Loading -> currentLocation.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Response.Success -> {
                        res.data?.let { geo ->
                            currentLocation.update {
                                it.copy(
                                    location = it.location.copy(
                                        city = city ?: "",
                                        state = city ?: "",
                                        country = ""
                                    ),
                                    isLoading = false,
                                    error = ""
                                )
                            }
                            if (geo.isNotEmpty()) {
                                updateUiStateWithCoordinates(geo[0])
                            }
                        }
                    }

                    is Response.Error -> {
                        currentLocation.update {
                            it.copy(
                                isLoading = false,
                                error = res.message ?: "Something went wrong."
                            )
                        }
                        _uiState.update {
                            it.copy(
                                currentWeather = CurrentWeather(),
                                isLoading = false,
                                error = res.message ?: "Coordinates From Name error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentAndForecastsWeatherData(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentAndForecastsWeatherDataUseCase.invoke(lat, long).collectLatest { res ->
                when (res) {
                    is Response.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Response.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }

                    is Response.Success -> {
                        if (res.data == null) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "Sorry can't fetch weather right now."
                                )
                            }
                        } else {
                            val currentWeather = CurrentWeather(
                                currentAndForecastsWeatherDataResponse = res.data,
                                userLocation = currentLocation.value.location
                            )
                            _uiState.update {
                                it.copy(
                                    currentWeather = currentWeather,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}