package com.example.jetpackcompose.data.DataSource

import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {
    suspend fun getWeatherData(token: String, city: String): Single<WeatherData>
}