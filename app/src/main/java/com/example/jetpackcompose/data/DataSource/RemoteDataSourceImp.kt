package com.example.jetpackcompose.data.DataSource

import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.network.ApiService
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImp(private val apiService: ApiService) : RemoteDataSource{
    override suspend fun getWeatherData(token: String, city: String): Single<WeatherData> {
        return apiService.getWeatherData(token, city)
    }
}