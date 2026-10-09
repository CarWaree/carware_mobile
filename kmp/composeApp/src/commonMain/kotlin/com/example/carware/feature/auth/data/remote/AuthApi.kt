package com.example.carware.feature.auth.data.remote

import com.example.carware.core.network.ApiResult
import com.example.carware.core.network.safeApiCall
import com.example.carware.network.api.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {

    suspend fun signUp(request: SignUpRequest): ApiResult<SignUpResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    suspend fun googleSignIn(request: GoogleSignInRequest): ApiResult<GoogleSignInResponse> =
        safeApiCall {
            client.post("$BASE_URL/api/Auth/google-mobile") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }


    companion object {
        private const val BASE_URL = "https://tbvzs781-8081.uks1.devtunnels.ms"
    }
}