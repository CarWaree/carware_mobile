package com.example.carware.cache

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class UserProfile @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val username: String,
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val loginTime: Long = kotlin.time.Clock.System.now().toEpochMilliseconds(),
)