package com.example.carware.network.apiResponse.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ServiceCentersResponse(
    val data: List<Centers>,
    val statusCode: Int,
    val message: String

)

@Serializable
data class Centers(
    val id: Int?=null,
    val name: String?=null,
    val location: String?=null,
    val phone: String?=null
)