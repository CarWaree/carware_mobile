package com.example.carware.network.apiResponse.reminderr

import kotlinx.serialization.Serializable

@Serializable

data class ReminderResponse(

    val data: Reminder,
    val statusCode: Int,
    val message: String


    )
@Serializable

data class Reminder (
    val id: Int,
    val notificationDate: String,
    val typeName: String,
    val vehicleName: String,
    val note: String,

    // fields  to schedule local notifications later
    val repeatInterval: Int,
    val repeatUnit: String,
    val repeatCount: Int


    )