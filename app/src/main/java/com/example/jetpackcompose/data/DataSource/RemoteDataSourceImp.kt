package com.example.jetpackcompose.data.DataSource

import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.network.ApiService
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

class RemoteDataSourceImp(private val apiService: ApiService) : RemoteDataSource{
    override fun getWeatherData(token: String, city: String): Flow<ForecastWeatherData> = flow{
        val response = apiService.getWeatherData(token, city)
        emit(response)
    }.flowOn(Dispatchers.IO) //Runs API call on background thread
}