package com.example.carware.network.api

import com.example.carware.network.core.ApiResult
import com.example.carware.network.apiResponse.history.GetHistoryItemResponse
import com.example.carware.network.apiResponse.history.GetHistoryResponse
import com.example.carware.network.core.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

suspend fun getHistory(client: HttpClient): ApiResult<List<GetHistoryResponse>> =
    safeApiCall {
        client.get("$baseUrl/api/history")
    }

suspend fun getHistoryItem(client: HttpClient,id: Int?): ApiResult<GetHistoryItemResponse> =
    safeApiCall {
        client.get { "$baseUrl/api/history/$id" }
    }
