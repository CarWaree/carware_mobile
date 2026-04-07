package com.example.carware.network.apiResponse.profile

import kotlinx.serialization.Serializable

@Serializable

data class GetProfileResponse(
    val data: ProfileDetails,
    val statusCode: String,
    val message: String

)
@Serializable

data class ProfileDetails(
    val fullName: String =" ",
    val phoneNumber: String?="01028472447 ",
    val email : String,
    val profileImageUrl: String =" "
)