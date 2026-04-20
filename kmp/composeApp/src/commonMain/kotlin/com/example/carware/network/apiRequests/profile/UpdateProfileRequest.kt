package com.example.carware.network.apiRequests.profile

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val fullName: String?="",
    val phoneNumber: String?="",
    val pendingEmail : String?="",
)