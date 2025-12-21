package com.example.carware.repository

import com.example.carware.network.api.getServiceCenters
import com.example.carware.network.api.getServiceType
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service

class ServiceRepository {

    suspend fun getServiceTypeRepo(): List<Service> {
        return getServiceType()
    }
    suspend fun getServiceCentersRepo(): List<Centers> {
        return getServiceCenters()
    }


}