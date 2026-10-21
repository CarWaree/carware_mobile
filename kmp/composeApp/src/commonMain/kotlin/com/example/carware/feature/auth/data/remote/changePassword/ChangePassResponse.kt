package com.example.carware.feature.auth.data.remote.changePassword

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassResponse(
    val statusCode: Int,
    val message: String
)
