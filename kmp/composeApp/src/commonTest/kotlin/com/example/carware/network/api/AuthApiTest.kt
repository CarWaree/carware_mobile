package com.example.carware.network.api

import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
import com.example.carware.network.apiRequests.auth.OTPRequest
import com.example.carware.network.apiRequests.auth.ResetPasswordRequest
import com.example.carware.network.apiRequests.auth.SignUpRequest
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AuthApiTest {

    private fun createMockClient(
        responseContent: String,
        statusCode: HttpStatusCode
    ): HttpClient {
        val mockEngine = MockEngine { _ ->
            respond(
                content = responseContent,
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun testTC01_SignupValidData() = runTest {
        val mockJsonResponse = """
            {
                "data": {
                    "firstName": "John",
                    "lastName": "Doe",
                    "username": "johndoe",
                    "email": "john@example.com",
                    "isEmailVerified": false
                },
                "statusCode": 201,
                "message": "Account created successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.Created)
        val request = SignUpRequest(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "SecurePass123",
            confirmPassword = "SecurePass123",
            userName = "JohnDOew"
        )

        val response = signupUser(request, mockClient)

        assertEquals("Account created successfully", response.message)
        assertEquals(201, response.statusCode)
        assertNotNull(response.data)
        assertEquals("John", response.data.firstName)
    }

    @Test
    fun testTC02_SignupExistingEmail() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "firstName": "",
                    "lastName": "",
                    "username": "",
                    "email": "",
                    "isEmailVerified": false
                },
                "statusCode": 409,
                "message": "Email already exists"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Conflict)
        val request = SignUpRequest(
            firstName = "Marwan",
            lastName = "ElAttar",
            email = "existing@email.com",
            password = "SecurePass123",
            confirmPassword = "SecurePass123",
            userName = "JohnDOew"
        )

        val exception = assertFailsWith<Exception> {
            signupUser(request, mockClient)
        }

        assertEquals("Email already exists", exception.message)
    }

    @Test
    fun testTC03_LoginValidCredentials() = runTest {
        val mockJsonResponse = """
            {
                "data": {
                    "isAuthenticated": true,
                    "firstName": "John",
                    "lastName": "Doe",
                    "username": "johndoe",
                    "email": "john@example.com",
                    "roles": ["USER"],
                    "accessToken": "token",
                    "refreshToken": "refresh",
                    "refreshTokenExpiration": "2024-12-31T23:59:59Z"
                },
                "statusCode": 200,
                "message": "User logged in successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = LoginRequest(
            emailOrUsername = "john@example.com",
            password = "SecurePass123"
        )

        val response = loginUser(request, mockClient)

        assertEquals("User logged in successfully", response.message)
        assertEquals(200, response.statusCode)
        assertNotNull(response.data)
        assertTrue(response.data!!.isAuthenticated)
    }

    @Test
    fun testTC04_LoginWrongPassword() = runTest {
        val errorJsonResponse = """
            {
                "data": null,
                "statusCode": 401,
                "message": "Authentication failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)
        val request = LoginRequest(
            emailOrUsername = "john@example.com",
            password = "wrongpass"
        )

        val exception = assertFailsWith<Exception> {
            loginUser(request, mockClient)
        }

        assertEquals("Authentication failed", exception.message)
    }

    @Test
    fun testTC05_EmailVerificationOTP() = runTest {
        val mockJsonResponse = """
            {
                "data": {
                    "accessToken": "some_token",
                    "refreshToken": "some_refresh_token"
                },
                "statusCode": 200,
                "message": "Email verified"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = EmailVerificationRequest(
            email = "john@example.com",
            otp = "123456"
        )

        val response = verifyEmailUser(request, mockClient)
        assertEquals("Email verified", response.message)
        assertEquals(200, response.statusCode)
    }

    @Test
    fun testTC06_ForgotPassword() = runTest {
        val mockJsonResponse = """
            {
                "statusCode": 200,
                "message": "OTP sent to email"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = ForgotPasswordRequest(
            email = "john@example.com"
        )

        val response = forgotPasswordUser(request, mockClient)
        assertEquals("OTP sent to email", response.message)
        assertEquals(200, response.statusCode)
    }

    @Test
    fun testTC07_ResetPassword() = runTest {
        val mockJsonResponse = """
            {
                "statusCode": 200,
                "message": "Password updated successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = ResetPasswordRequest(
            accessToken = "123",
            newPassword = "NewSecurePass456",
            confirmPassword = "NewSecurePass456"
        )

        val response = resetPasswordUser(request, mockClient)
        assertEquals("Password updated successfully", response.message)
        assertEquals(200, response.statusCode)
    }

    @Test
    fun testTC08_SignupMismatchedPasswords() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "firstName": "",
                    "lastName": "",
                    "username": "",
                    "email": "",
                    "isEmailVerified": false
                },
                "statusCode": 400,
                "message": "Signup failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = SignUpRequest(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "SecurePass123",
            confirmPassword = "DifferentPass123",
            userName = "johndoe"
        )

        val exception = assertFailsWith<Exception> {
            signupUser(request, mockClient)
        }

        assertEquals("Signup failed", exception.message)
    }

    @Test
    fun testTC09_SignupInvalidEmailFormat() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "firstName": "",
                    "lastName": "",
                    "username": "",
                    "email": "",
                    "isEmailVerified": false
                },
                "statusCode": 400,
                "message": "Signup failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = SignUpRequest(
            firstName = "John",
            lastName = "Doe",
            email = "invalid-email",
            password = "SecurePass123",
            confirmPassword = "SecurePass123",
            userName = "johndoe"
        )

        val exception = assertFailsWith<Exception> {
            signupUser(request, mockClient)
        }

        assertEquals("Signup failed", exception.message)
    }

    @Test
    fun testTC10_LoginNonExistentEmail() = runTest {
        val errorJsonResponse = """
            {
                "data": null,
                "statusCode": 404,
                "message": "login failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)
        val request = LoginRequest(
            emailOrUsername = "nonexistent@example.com",
            password = "password123"
        )

        val exception = assertFailsWith<Exception> {
            loginUser(request, mockClient)
        }

        assertEquals("login failed", exception.message)
    }

    @Test
    fun testTC11_EmailVerificationWrongOTP() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "accessToken": "",
                    "refreshToken": ""
                },
                "statusCode": 400,
                "message": "otp verify failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = EmailVerificationRequest(
            email = "john@example.com",
            otp = "000000"
        )

        val exception = assertFailsWith<Exception> {
            verifyEmailUser(request, mockClient)
        }

        assertEquals("otp verify failed", exception.message)
    }

    @Test
    fun testTC12_EmailVerificationExpiredOTP() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "accessToken": "",
                    "refreshToken": ""
                },
                "statusCode": 410,
                "message": "otp verify failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Gone)
        val request = EmailVerificationRequest(
            email = "john@example.com",
            otp = "123456"
        )

        val exception = assertFailsWith<Exception> {
            verifyEmailUser(request, mockClient)
        }

        assertEquals("otp verify failed", exception.message)
    }

    @Test
    fun testTC13_ForgotPasswordUnregisteredEmail() = runTest {
        val errorJsonResponse = """
            {
                "statusCode": 404,
                "message": "forgotPassword failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.NotFound)
        val request = ForgotPasswordRequest(
            email = "unregistered@example.com"
        )

        val exception = assertFailsWith<Exception> {
            forgotPasswordUser(request, mockClient)
        }

        assertEquals("forgotPassword failed", exception.message)
    }

    @Test
    fun testTC14_ResetPasswordMismatchedPasswords() = runTest {
        val errorJsonResponse = """
            {
                "statusCode": 400,
                "message": "resetPassword failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = ResetPasswordRequest(
            accessToken = "token123",
            newPassword = "NewPassword123",
            confirmPassword = "DifferentPassword123"
        )

        val exception = assertFailsWith<Exception> {
            resetPasswordUser(request, mockClient)
        }

        assertEquals("resetPassword failed", exception.message)
    }

    @Test
    fun testTC15_ResetPasswordInvalidToken() = runTest {
        val errorJsonResponse = """
            {
                "statusCode": 401,
                "message": "resetPassword failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)
        val request = ResetPasswordRequest(
            accessToken = "invalid_token",
            newPassword = "NewPassword123",
            confirmPassword = "NewPassword123"
        )

        val exception = assertFailsWith<Exception> {
            resetPasswordUser(request, mockClient)
        }

        assertEquals("resetPassword failed", exception.message)
    }

    @Test
    fun testTC16_OTPVerificationValid() = runTest {
        val mockJsonResponse = """
            {
                "data": {
                    "token": "valid_token"
                },
                "statusCode": 200,
                "message": "OTP verified successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = OTPRequest(
            email = "john@example.com",
            otp = "123456"
        )

        val response = otpVerificationUser(request, mockClient)

        assertEquals("OTP verified successfully", response.message)
        assertEquals(200, response.statusCode)
        assertNotNull(response.data)
        assertEquals("valid_token", response.data.token)
    }

    @Test
    fun testTC17_OTPVerificationWrongCode() = runTest {
        val errorJsonResponse = """
            {
                "data": {
                    "token": ""
                },
                "statusCode": 400,
                "message": "otpVerification failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = OTPRequest(
            email = "john@example.com",
            otp = "000000"
        )

        val exception = assertFailsWith<Exception> {
            otpVerificationUser(request, mockClient)
        }

        assertEquals("otpVerification failed", exception.message)
    }
}
