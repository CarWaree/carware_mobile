package com.example.carware.network.apiResponse.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ServiceTypesResponse(
    val data: List<Service>,
    val statusCode: Int,
    val message: String

)

@Serializable
data class Service(
    val name: String
)