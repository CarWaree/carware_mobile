package com.example.carware.network.api

import com.example.carware.network.apiRequests.vehicle.UpdateVehicleRequest
import com.example.carware.network.apiRequests.vehicle.VehicleRequest
import com.example.carware.network.apiResponse.vehicle.Brand
import com.example.carware.network.apiResponse.vehicle.BrandResponse
import com.example.carware.network.apiResponse.vehicle.DeleteVehicleResponse
import com.example.carware.network.apiResponse.vehicle.GetVehicleResponse
import com.example.carware.network.apiResponse.vehicle.Model
import com.example.carware.network.apiResponse.vehicle.ModelResponse
import com.example.carware.network.apiResponse.vehicle.UpdateVehicleResponse
import com.example.carware.network.apiResponse.vehicle.VehicleResponse
import com.example.carware.network.core.ApiResult
import com.example.carware.network.core.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.parameters

suspend fun getBrands(
    page: Int = 1,
    pageSize: Int = 100,
    client: HttpClient
): ApiResult<BrandResponse> =
    safeApiCall {
        client.get("$baseUrl/api/Vehicle/brands") {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }
    }

suspend fun getModels(
    brandId: Int?,
    client: HttpClient
): ApiResult<ModelResponse> =
    safeApiCall {
        client.get("$baseUrl/api/Vehicle/models") {
            parameter("brandId", brandId)
        }
    }

suspend fun getVehicles(client: HttpClient): ApiResult<GetVehicleResponse> =
    safeApiCall {
        client.get {
            url("$baseUrl/api/Vehicle/my-vehicles")
        }
    }

suspend fun getVehicle(client: HttpClient, id: Int): ApiResult<VehicleResponse> =
    safeApiCall {
        client.get {
            url("$baseUrl/api/Vehicle/$id")
        }
    }

suspend fun addVehicles(
    request: VehicleRequest,
    client: HttpClient
): ApiResult<VehicleResponse> =
    safeApiCall {
        client.post { // Store the full HttpResponse
            url("$baseUrl/api/Vehicle")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }


suspend fun updateVehicle(
    client: HttpClient,
    id: Int,
    request: UpdateVehicleRequest

): ApiResult<UpdateVehicleResponse> =
    safeApiCall {
        client.put {
            url("$baseUrl/api/vehicle/$id")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

suspend fun deleteVehicle(
    client: HttpClient,
    id: Int
): ApiResult<DeleteVehicleResponse> =
    safeApiCall {
        client.delete {
            url("$baseUrl/api/vehicle/$id")
            contentType(ContentType.Application.Json)
        }
    }