package com.tta.leanworx.weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tta.leanworx.weather.presentation.home.HomeScreen
import com.tta.leanworx.weather.ui.theme.LeanworxWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeanworxWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
    companion object{
        const val LOCATION_PERM_MESSAGE = "Allowing an app to access your location will provide you with the most accurate and up-to-date weather information based on your current location." +
                "It will also be more convenient for you as you don't have to manually enter your location."

        const val BOTTOM_PADDING =  60
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LeanworxWeatherTheme {
        HomeScreen()
    }
}