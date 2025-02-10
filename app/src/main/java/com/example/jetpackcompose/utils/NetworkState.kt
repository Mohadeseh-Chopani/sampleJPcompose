package com.example.jetpackcompose.utils

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error<T>(val error: Throwable) : NetworkState<T>()
}