package com.example.carware.network.core

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(
        val code: Int,
        val message: String,
        val body: String?
    ) : ApiResult<T>()
    data class Exception<T>(val throwable: Throwable) : ApiResult<T>()
}
