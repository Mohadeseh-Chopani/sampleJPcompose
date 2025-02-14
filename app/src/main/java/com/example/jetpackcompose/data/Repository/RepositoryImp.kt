package com.example.jetpackcompose.data.Repository

import com.example.jetpackcompose.data.DataSource.RemoteDataSource
import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImp(private val remoteDataSource: RemoteDataSource): Repository {

    override fun getWeatherData(token: String, city: String): Flow<ForecastWeatherData> {
        return remoteDataSource.getWeatherData(token, city)
    }
}