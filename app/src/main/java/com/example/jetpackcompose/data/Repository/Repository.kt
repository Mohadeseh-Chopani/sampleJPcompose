package com.example.jetpackcompose.data.Repository

import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface Repository {
     fun getWeatherData(token: String, city: String): Flow<ForecastWeatherData>
}