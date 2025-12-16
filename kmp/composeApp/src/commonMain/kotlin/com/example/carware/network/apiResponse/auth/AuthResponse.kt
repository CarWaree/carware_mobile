package com.example.carware.network.apiResponse.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val data: UserData? = null,
    val statusCode: Int,
    val message: String
)
@Serializable
data class UserData(
    val isAuthenticated: Boolean,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<String>,
    val token: String,
    val expiresOn: String
)
//
//{
//    "data": {
//    "isAuthenticated": true,
//    "firstName": "string",
//    "lastName": "string",
//    "username": "string",
//    "email": "user@example.com",
//    "roles": [
//    "USER"
//    ],
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdHJpbmciLCJqdGkiOiJjY2U2MjI5Ny05MTMxLTRjZDctYjVjYS02MWVhNGRkZmYyZWYiLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJ1aWQiOiJmY2I4ZjBjNC01ZjIxLTQ3ZWItOTA4Yy05OGRmZmIwMWRjNTMiLCJmaXJzdE5hbWUiOiJzdHJpbmciLCJsYXN0TmFtZSI6InN0cmluZyIsInJvbGVzIjoiVVNFUiIsImV4cCI6MTc2NTM5NDEyMCwiaXNzIjoiQ2FyV2FyZS1BUEkiLCJhdWQiOiJDYXJXYXJlLVVzZXJzIn0.vEUvcRvYzQEnfWYmbx2m6N9RfMSJZmeoRQvM8E1PQCU",
//    "expiresOn": "2025-12-10T19:15:20Z"
//},
//    "statusCode": 200,
//    "message": "Registration successful"
//}