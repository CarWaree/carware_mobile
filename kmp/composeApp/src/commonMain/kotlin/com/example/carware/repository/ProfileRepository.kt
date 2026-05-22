package com.example.carware.repository

import com.example.carware.cache.profileStore
import com.example.carware.network.api.getProfile
import com.example.carware.network.api.updateProfile
import com.example.carware.network.api.uploadProfileImage
import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.ProfileDetails
import com.example.carware.network.apiResponse.profile.UpdatePictureResponse
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.network.cache.ProfileCacheData
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.UiResult
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class ProfileRepository(
    private val client: HttpClient
) {
    suspend fun getProfileRepo(): GetProfileResponse {
        return when (val result = getProfile(client)) {
            is ApiResult.Success -> {
                val response = result.data
                response.data?.let { profile ->
                    profileStore.set(ProfileCacheData(profile))
                }
                response
            }
            is ApiResult.Error -> {
                val cached = profileStore.get()
                if (cached?.profile != null) {
                    GetProfileResponse(
                        data = cached.profile,
                        statusCode = 200,
                        message = "Loaded from cache"
                    )
                } else {
                    throw Exception(result.message)
                }
            }
            is ApiResult.Exception -> {
                val cached = profileStore.get()
                if (cached?.profile != null) {
                    GetProfileResponse(
                        data = cached.profile,
                        statusCode = 200,
                        message = "Loaded from cache"
                    )
                } else {
                    throw Exception(result.throwable.message ?: "Unknown error occurred")
                }
            }
        }
    }

    suspend fun updateProfileRepo(request: UpdateProfileRequest): UiResult<UpdateProfileResponse> {
        return when(val result= updateProfile(request, client)){
            is ApiResult.Success -> {
                UiResult.Success(result.data)
            }
            is ApiResult.Error -> {
                UiResult.Error(result.message)
            }
            is ApiResult.Exception -> {
                UiResult.Error(result.throwable.message ?: "Unknown error occurred")
            }
        }
    }

    // Add this to your existing ProfileRepository
    suspend fun uploadProfileImageRepo(imageBytes: ByteArray):
            UiResult<UpdatePictureResponse> {
         return when ( val result =uploadProfileImage(imageBytes, client)){
             is ApiResult.Success -> {
                 UiResult.Success(result.data)
             }
             is ApiResult.Error -> {
                 UiResult.Error(result.message)
             }
             is ApiResult.Exception -> {
                 UiResult.Error(result.throwable.message ?: "Unknown error occurred")
             }
         }
    }
}