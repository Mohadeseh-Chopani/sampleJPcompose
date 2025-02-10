package com.example.jetpackcompose.data.Repository

import com.example.jetpackcompose.data.DataSource.RemoteDataSource
import com.example.jetpackcompose.data.WeatherData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RepositoryImp(private val remoteDataSource: RemoteDataSource): Repository {
    private var compositeDisposable: CompositeDisposable? = null

    override suspend fun getWeatherData(token: String, city: String): Single<WeatherData> {
        return remoteDataSource.getWeatherData(token, city)
            .doOnSubscribe { disposable -> compositeDisposable?.add(disposable) }
    }

    fun clearDisposable() {
        compositeDisposable?.let { compositeDisposable!!.clear() }
    }
}