package com.example.carware.network.api

import com.example.carware.network.apiRequests.auth.EmailVerificationRequest
import com.example.carware.network.apiRequests.auth.ForgotPasswordRequest
import com.example.carware.network.apiRequests.auth.LoginRequest
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

class AuthApiTest {

    // Helper function to build a fake Ktor client
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

    // --- TC-01: User Signup (Valid Data) ---
    @Test
    fun testTC01_SignupValidData() = runTest {
        val mockJsonResponse = """
            {
                "message": "Account created successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = SignUpRequest("Marwan", "elattar","3atto","test@email.com", "password123","password123") // Adjust properties


        val response = signupUser(request, mockClient)

        assertEquals("Account created successfully", response.message)
    }

    // --- TC-02: Signup with Existing Email ---
    @Test
    fun testTC02_SignupExistingEmail() = runTest {
        val errorJsonResponse = """
            {
                "message": "Email already exists"
            }
        """.trimIndent()

        // Simulating a 400 Bad Request or 409 Conflict from your server
        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.BadRequest)
        val request = SignUpRequest("Marwan","elattar", "atar","existing@email.com", "password123","password123") // Adjust properties

        val exception = assertFailsWith<Exception> {
            signupUser(request, mockClient)
        }

        assertEquals("Email already exists", exception.message)
    }

    // --- TC-03: Login with Valid Credentials ---
    @Test
    fun testTC03_LoginValidCredentials() = runTest {
        val mockJsonResponse = """
            {
                "message": "User logged in successfully",
                "token": "fake_jwt_token"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = LoginRequest("test@email.com", "password123")

        val response = loginUser(request, mockClient)

        assertEquals("User logged in successfully", response.message)
    }

    // --- TC-04: Login with Wrong Password ---
    @Test
    fun testTC04_LoginWrongPassword() = runTest {
        val errorJsonResponse = """
            {
                "message": "Authentication failed"
            }
        """.trimIndent()

        val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Unauthorized)
        val request = LoginRequest("test@email.com", "wrongpass")

        val exception = assertFailsWith<Exception> {
            loginUser(request, mockClient)
        }

        assertEquals("Authentication failed", exception.message)
    }

    // --- TC-05: Email Verification OTP ---
    @Test
    fun testTC05_EmailVerificationOTP() = runTest {
        val mockJsonResponse = """
            {
                "message": "Email verified"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = EmailVerificationRequest("test@email.com", "123456") // Adjust properties

        val response = verifyEmailUser(request, mockClient)

        assertEquals("Email verified", response.message)
    }

    // --- TC-06: Forgot Password ---
    @Test
    fun testTC06_ForgotPassword() = runTest {
        val mockJsonResponse = """
            {
                "message": "OTP sent to email"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = ForgotPasswordRequest("test@email.com") // Adjust properties

        val response = forgotPasswordUser(request, mockClient)

        assertEquals("OTP sent to email", response.message)
    }

    // --- TC-07: Reset Password ---
    @Test
    fun testTC07_ResetPassword() = runTest {
        val mockJsonResponse = """
            {
                "message": "Password updated successfully"
            }
        """.trimIndent()

        val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
        val request = ResetPasswordRequest("test@email.com", "123456", "newPassword123") // Adjust properties

        val response = resetPasswordUser(request, mockClient)

        assertEquals("Password updated successfully", response.message)
    }
}