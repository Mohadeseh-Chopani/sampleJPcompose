package com.example.jetpackcompose.network

import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?action=hourly")
    suspend fun getWeatherData(
        @Query("token") token: String,
        @Query("city") city: String
    ): Single<WeatherData>
}