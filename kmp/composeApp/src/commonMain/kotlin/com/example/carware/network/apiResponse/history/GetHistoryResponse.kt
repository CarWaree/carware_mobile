package com.example.carware.network.apiResponse.history

import kotlinx.serialization.Serializable

//@Serializable
//data class GetHistoryResponse(
//    val data: List<HistoryItems>,
//    val statusCode: String,
//    val message: String
//)
@Serializable
data class GetHistoryResponse(
    val id: Int,
    val carName: String,
    val providerName:String,
    val date:String,
    val totalPrice:String,
    val paymentMethod:String,
    )