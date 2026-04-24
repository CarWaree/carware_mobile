package com.example.carware.network.apiResponse.reminderr

import kotlinx.serialization.Serializable

@Serializable
data class GetReminderResponse(
    val data: List<Reminder>,
    val statusCode: Int,
    val message: String
)
