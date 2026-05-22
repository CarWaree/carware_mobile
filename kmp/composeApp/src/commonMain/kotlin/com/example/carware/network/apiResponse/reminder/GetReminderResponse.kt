package com.example.carware.network.apiResponse.reminder

import kotlinx.serialization.Serializable

@Serializable
data class GetReminderResponse(
    val data: List<Reminder>,
    val statusCode: Int,
    val message: String
)
