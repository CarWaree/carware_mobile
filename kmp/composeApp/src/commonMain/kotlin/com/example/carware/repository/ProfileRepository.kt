package com.example.carware.repository

import com.example.carware.cache.profileStore
import com.example.carware.network.api.getProfile
import com.example.carware.network.api.updateProfile
import com.example.carware.network.api.uploadProfileImage
import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.ProfileDetails
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.network.cache.ProfileCacheData
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class ProfileRepository(
    private val client: HttpClient
) {

    suspend fun getProfileRepo(): GetProfileResponse {
        return try {
            val response = getProfile(client)

            // Cache the profile data
            response.data?.let { profile ->
                profileStore.set(ProfileCacheData(profile))
            }

            response

        } catch (e: Exception) {
            val cached = profileStore.get()

            if (cached?.profile != null) {
                // Return cached data with success status
                GetProfileResponse(
                    data = cached.profile,
                    statusCode = 200,
                    message = "Loaded from cache"
                )
            } else {
                // No cache, return error response
                GetProfileResponse(
                    data = ProfileDetails(
                        fullName = "",
                        email = "",
                        profileImageUrl = ""
                    ),
                    statusCode = 500,
                    message = "No internet connection"
                )
            }
        }
    }

    suspend fun updateProfileRepo(request: UpdateProfileRequest): UpdateProfileResponse {
        return updateProfile(request, client)
    }

    // Add this to your existing ProfileRepository
    suspend fun uploadProfileImageRepo(imageBytes: ByteArray) {
        uploadProfileImage(imageBytes, client)
    }
}