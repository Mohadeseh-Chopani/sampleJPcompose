package com.example.jetpackcompose.data.Repository

import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single

interface Repository {
    suspend fun getWeatherData(token: String, city: String): Single<WeatherData>
}