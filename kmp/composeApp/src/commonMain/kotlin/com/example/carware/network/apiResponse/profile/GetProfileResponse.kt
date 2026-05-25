package com.example.carware.network.apiResponse.profile

import com.example.carware.network.api.baseUrl
import kotlinx.serialization.Serializable

@Serializable

data class GetProfileResponse(
    val data: ProfileDetails,
    val statusCode: Int,
    val message: String

)
@Serializable

data class ProfileDetails(
    val fullName: String =" ",
    val phoneNumber: String?=null,
    val email : String,
    val profileImageUrl: String =" "
)