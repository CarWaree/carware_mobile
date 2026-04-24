package com.example.carware.network.apiRequests.reminder

import kotlinx.serialization.Serializable


@Serializable

data class ReminderRequest(

    val notificationDate: String,
    val repeatInterval: Int,
    val repeatUnit: String,
    val repeatCount: Int,
    val note: String,
    val typeId: Int,
    val vehicleId: Int

)