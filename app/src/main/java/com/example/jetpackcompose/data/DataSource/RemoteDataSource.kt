package com.example.jetpackcompose.data.DataSource

import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
     fun getWeatherData(token: String, city: String): Flow<ForecastWeatherData>
}