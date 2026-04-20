package com.example.carware.repository

import com.example.carware.network.api.getProfile
import com.example.carware.network.api.updateProfile
import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.util.storage.PreferencesManager
import io.ktor.client.HttpClient

class ProfileRepository(
    private val client: HttpClient) {

    suspend fun getProfileRepo(): GetProfileResponse {

        return getProfile(
            client)
    }

    suspend fun updateProfileRepo(request: UpdateProfileRequest): UpdateProfileResponse {
        return updateProfile( request,client)
    }
}