package com.example.carware.network.apiResponse.history

import kotlinx.serialization.Serializable

@Serializable

data class GetHistoryItemResponse(
    val id: Int,
    val carName: String,
    val providerName:String,
    val date:String,
    val totalPrice:Int,
    val paymentMethod:String,
    val serviceDetails: String? = null,
)
