package com.example.carware.network.core


sealed class UiResult<T> {
    data class Success<T>(val data: T) : UiResult<T>()
    data class Error<T>(val message: String) : UiResult<T>()
}