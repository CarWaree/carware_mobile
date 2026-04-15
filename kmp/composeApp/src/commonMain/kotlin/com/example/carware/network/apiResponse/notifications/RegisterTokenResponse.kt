package com.example.carware.network.apiResponse.notifications

import carware.composeapp.generated.resources.Res
import kotlinx.serialization.Serializable
import org.koin.core.logger.MESSAGE

@Serializable
data class RegisterTokenResponse(
    val message: String,
)

