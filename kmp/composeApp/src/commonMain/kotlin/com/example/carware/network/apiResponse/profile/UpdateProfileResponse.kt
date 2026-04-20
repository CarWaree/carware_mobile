package com.example.carware.network.apiResponse.profile

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileResponse (
    val data: ProfileDetails,
    val statusCode: String,
    val message: String,
)