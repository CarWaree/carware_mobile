package com.example.carware.network.apiResponse.profile

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePictureResponse(
    val statusCode: Int,
    val message: String
)
