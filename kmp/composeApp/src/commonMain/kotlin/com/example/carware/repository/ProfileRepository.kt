package com.example.carware.repository

import com.example.carware.network.api.getProfile
import com.example.carware.network.api.updateProfile
import com.example.carware.network.apiRequests.profile.UpdateProfileRequest
import com.example.carware.network.apiResponse.profile.GetProfileResponse
import com.example.carware.network.apiResponse.profile.UpdateProfileResponse
import com.example.carware.util.storage.PreferencesManager

class ProfileRepository(private val prefs: PreferencesManager) {

    suspend fun getProfileRepo(): GetProfileResponse {
        val token = prefs.getToken() ?: throw Exception("Token not found")
        return getProfile(token)
    }

    suspend fun updateProfileRepo(request: UpdateProfileRequest): UpdateProfileResponse {
        val token = prefs.getToken() ?: throw Exception("Token not found")
        return updateProfile(token, request)
    }
}