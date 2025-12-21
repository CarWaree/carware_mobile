package com.example.carware.repository

import com.example.carware.network.Api.getBrands
import com.example.carware.network.Api.getServiceCenters
import com.example.carware.network.Api.getServiceType
import com.example.carware.network.apiResponse.schedule.Centers
import com.example.carware.network.apiResponse.schedule.Service
import com.example.carware.network.apiResponse.vehicle.Brand

class ServiceRepository {

    suspend fun getServiceTypeRepo(): List<Service> {
        return getServiceType()
    }
    suspend fun getServiceCentersRepo(): List<Centers> {
        return getServiceCenters()
    }


}