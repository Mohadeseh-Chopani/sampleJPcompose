package com.example.jetpackcompose.view

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.Repository.Repository
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.utils.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: Repository) : ViewModel() {

    var _weatherData = MutableLiveData<NetworkState<WeatherData>>()
    val weatherData: LiveData<NetworkState<WeatherData>>  get() = _weatherData
    @SuppressLint("CheckResult")
    suspend fun LoadData(token: String, city: String) {
        _weatherData.value = NetworkState.Loading

        repository.getWeatherData(token, city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _weatherData.value = NetworkState.Success(response)
            }, { throwable ->
                _weatherData.value = NetworkState.Error(throwable)
            })
    }
}