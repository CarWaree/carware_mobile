package com.example.carware.feature.auth.data.mapper

import com.example.carware.feature.auth.data.remote.changePassword.ChangePassRequest
import com.example.carware.feature.auth.data.remote.changePassword.ChangePassResponse
import com.example.carware.feature.auth.domain.model.ChangePassResult

fun ChangePassResponse.toDomain() = ChangePassResult(message = message)
