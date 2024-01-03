package com.tta.leanworx.weather.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tta.leanworx.weather.R
import com.tta.leanworx.weather.data.utils.dateTimeFromUnixUtc
import com.tta.leanworx.weather.presentation.states.CurrentWeather
import com.tta.leanworx.weather.presentation.states.UIState
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
val sdf = SimpleDateFormat("MMMM dd")

@Composable
fun HomeSuccessScreen(
    uiState: UIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = sdf.format(Date()).orEmpty(),
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = R.drawable.weather_sunset_up), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.currentWeather.currentAndForecastsWeatherDataResponse.sys?.sunrise?.let { if(it.isBlank()) "" else dateTimeFromUnixUtc(it.toInt()) }.toString(),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.weather_sunset_down), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.currentWeather.currentAndForecastsWeatherDataResponse.sys?.sunset?.let { if(it.isBlank()) "" else dateTimeFromUnixUtc(it.toInt()) }.toString(),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        AsyncImage(
            modifier = Modifier.size(64.dp),
            model = "https://openweathermap.org/img/w/" + uiState.currentWeather.currentAndForecastsWeatherDataResponse.weather.let { if (it.isEmpty()) "" else it[0]?.icon.orEmpty() } + ".png",
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholder)
        )
        Text(
            text = stringResource(
                R.string.temperature_value,
                uiState.currentWeather.currentAndForecastsWeatherDataResponse.main?.temp.orEmpty()
            ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            text = uiState.currentWeather.currentAndForecastsWeatherDataResponse.weather.let { if (it.isEmpty()) "" else it[0]?.description.orEmpty() },
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(
                R.string.feels_like_temperature_value,
                uiState.currentWeather.currentAndForecastsWeatherDataResponse.main?.feelsLike.orEmpty()
            ),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview
fun HomeSuccessScreenPreview() {
    HomeSuccessScreen(uiState = UIState(CurrentWeather(), false, ""))
}