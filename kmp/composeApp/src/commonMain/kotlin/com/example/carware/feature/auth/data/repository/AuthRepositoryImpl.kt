package com.example.carware.feature.auth.data.repository

import com.example.carware.core.network.ApiResult
import com.example.carware.feature.auth.data.mapper.toDomain
import com.example.carware.feature.auth.data.remote.AuthApi
import com.example.carware.feature.auth.data.remote.GoogleSignInRequest
import com.example.carware.feature.auth.data.remote.SignUpRequest
import com.example.carware.feature.auth.domain.model.GoogleSignInResult
import com.example.carware.feature.auth.domain.model.SignUpResult
import com.example.carware.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ApiResult<SignUpResult> {
        val request = SignUpRequest(
            firstName = firstName,
            lastName = lastName,
            userName = userName,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
        return when (val result = api.signUp(request)) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> ApiResult.Error(result.code, result.message, result.body)
            is ApiResult.Exception -> ApiResult.Exception(result.throwable)
        }
    }
    override suspend fun googleSignIn(idToken: String): ApiResult<GoogleSignInResult> {
        val request = GoogleSignInRequest(idToken)
        return when (val result = api.googleSignIn(request)) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> ApiResult.Error(result.code, result.message, result.body)
            is ApiResult.Exception -> ApiResult.Exception(result.throwable)
        }
    }
}