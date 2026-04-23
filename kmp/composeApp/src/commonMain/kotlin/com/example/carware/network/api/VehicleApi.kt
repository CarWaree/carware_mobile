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
import com.example.carware.network.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
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

suspend fun getBrands(

    page: Int = 1,
    pageSize: Int = 100,
    client: HttpClient
): List<Brand> {
    return client.get("$baseUrl/api/Vehicle/brands") {
        parameter("page", page)
        parameter("pageSize", pageSize)
        contentType(ContentType.Application.Json)
    }.body<BrandResponse>().data
}

suspend fun getModels(
    brandId: Int?,
    client: HttpClient
): List<Model> {
    return client.get("$baseUrl/api/Vehicle/models") {
        parameter("brandId", brandId)
    }.body<ModelResponse>().data
}


suspend fun getVehicles(client: HttpClient): GetVehicleResponse {
    val response: HttpResponse = client.get {
        url("$baseUrl/api/Vehicle/my-vehicles")
        contentType(ContentType.Application.Json)
    }
    println("--- RESPONSE DEBUG getVehicles: Status: ${response.status.value}")

    if (response.status.isSuccess()) {
        return response.body<GetVehicleResponse>()
    } else {
        val errorBody = try {
            response.bodyAsText()
        } catch (e: Exception) {
            "Could not read error body."
        }
        println("--- ERROR DETAILS getVehicles: $errorBody")
        throw Exception("API Error (${response.status.value}): $errorBody")
    }
}
suspend fun getVehicle(client: HttpClient,id: Int): VehicleResponse{
    val response : HttpResponse=client.get {
        url("$baseUrl/api/Vehicle/$id")
        contentType(ContentType.Application.Json)
    }
    if (response.status.isSuccess()) {
        return response.body<VehicleResponse>()
    } else {
        val errorBody = try {
            response.bodyAsText()
        } catch (e: Exception) {
            "Could not read error body."
        }
        println("--- ERROR DETAILS getVehicles: $errorBody")
        throw Exception("Get Vehicle Error (${response.status.value}): $errorBody")
    }
}

suspend fun addVehicles(
    request: VehicleRequest,
    client: HttpClient
): VehicleResponse {

    val response: HttpResponse = client.post { // Store the full HttpResponse
        url("$baseUrl/api/Vehicle")
        contentType(ContentType.Application.Json)
        setBody(request)
    }


    if (response.status.isSuccess()) {
        return response.body<VehicleResponse>()
    } else {
        val errorBody = response.bodyAsText() // Get the raw error response
        throw Exception("API Error (${response.status}): $errorBody")
    }
}


suspend fun updateVehicle(
    client: HttpClient,
    id: Int,
    request: UpdateVehicleRequest

): UpdateVehicleResponse {
    val response: HttpResponse = client.put {
        url ("$baseUrl/api/vehicle/$id")
        contentType(ContentType.Application.Json)
        setBody(request)
    }
    if (response.status.isSuccess()) {
        // If successful, parse the body as the expected success response
        return response.body<UpdateVehicleResponse>()
    } else {
        // If it failed, throw an exception with the server's error message
        val errorBody = response.bodyAsText() // Get the raw error response
        throw Exception("Update car  Error (${response.status}): $errorBody")
    }

}

suspend fun deleteVehicle(
    client: HttpClient,
    id: Int
): DeleteVehicleResponse {
    val response: HttpResponse = client.delete {
        url("$baseUrl/api/vehicle/$id")
        contentType(ContentType.Application.Json)
    }
    if (response.status.isSuccess()) {
        // If successful, parse the body as the expected success response
        return response.body<DeleteVehicleResponse>()
    } else {
        // If it failed, throw an exception with the server's error message
        val errorBody = response.bodyAsText() // Get the raw error response
        throw Exception("Delete car  Error (${response.status}): $errorBody")
    }

}