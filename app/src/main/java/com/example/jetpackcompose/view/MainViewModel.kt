package com.example.jetpackcompose.view

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.Repository.Repository
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.utils.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _weatherData = MutableStateFlow<NetworkState<ForecastWeatherData>>(NetworkState.Loading)
    val weatherData: StateFlow<NetworkState<ForecastWeatherData>> = _weatherData.asStateFlow()

    fun loadData(token: String, city: String) {
        viewModelScope.launch {
            try {
                repository.getWeatherData(token, city)
                    .onStart { _weatherData.value = NetworkState.Loading }
                    .catch { e -> _weatherData.value = NetworkState.Error(e) }
                    .collect { response -> _weatherData.value = NetworkState.Success(response) }
            } catch (e: Exception) {
                _weatherData.value = NetworkState.Error(e)
            }
        }
    }

}