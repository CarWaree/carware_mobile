package com.example.carware.core.network


sealed class UiResult<T> {
    data class Success<T>(val data: T) : UiResult<T>()
    data class Error<T>(val message: String) : UiResult<T>()
}