package com.example.carware.network.cache

import com.example.carware.network.apiResponse.profile.ProfileDetails
import kotlinx.serialization.Serializable

@Serializable
data class ProfileCacheData(
    val profile: ProfileDetails?=null,
)
