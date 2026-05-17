package com.example.carware.network.api

import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.auth.AuthResponse
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.UpdatePictureResponse
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

suspend fun getProfile(
    client: HttpClient
): ApiResult<GetProfileResponse> =
    safeApiCall {
        client.get {
            url("$baseUrl/api/Profile")
            contentType(ContentType.Application.Json)
        }
    }

suspend fun updateProfile(
    request: UpdateProfileRequest,
    client: HttpClient
): ApiResult<UpdateProfileResponse> =
    safeApiCall {
        client.put { // Use .put for updates
            url("$baseUrl/api/Profile")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

suspend fun uploadProfileImage(
    imageBytes: ByteArray,
    client: HttpClient
): ApiResult<UpdatePictureResponse> =
    safeApiCall {
        client.post {
            url("$baseUrl/api/Profile/upload-image")
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            key = "file",
                            value = imageBytes,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=\"profile.jpg\"")
                            }
                        )
                    }
                )
            )
        }
    }
