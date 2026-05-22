package com.example.carware.network.cache

import com.example.carware.network.apiResponse.schedule.Service
import kotlinx.serialization.Serializable

@Serializable
data class ServicesCacheData(
    val services: List<Service> = emptyList(),
    val lastUpdated: String = " "
)