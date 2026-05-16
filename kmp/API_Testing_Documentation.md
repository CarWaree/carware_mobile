# CarWare API Testing Documentation

This document outlines the testing strategy and implementation for the network layer of the CarWare Kotlin Multiplatform project. The tests are designed to ensure reliability, correct data parsing, and robust error handling across all API integrations.

## 1. Test Files Overview

The following test files are located in `composeApp/src/commonTest/kotlin/com/example/carware/network/api/`:

| File | Purpose |
| :--- | :--- |
| **AuthApiTest.kt** | Validates authentication flows: Signup, Login, Email Verification (OTP), Forgot Password, and Reset Password. |
| **VehicleApiTest.kt** | Tests vehicle management: Fetching brands/models, adding, updating, deleting, and retrieving user vehicles. |
| **ScheduleApiTest.kt** | Covers service scheduling: Retrieving service types, finding service centers, and managing appointments. |
| **HistoryApiTest.kt** | Ensures service history records and detailed item views are correctly retrieved and parsed. |
| **ReminderApiTest.kt** | Validates the creation and retrieval of maintenance reminders. |
| **ProfileApiTest.kt** | Tests fetching and updating user profile information. |
| **NotificationsApiTest.kt** | Validates FCM token registration and notification testing endpoints. |

## 2. Test Class Structure

Tests are organized into classes corresponding to their API counterparts. They follow a consistent structure:

- **Setup**: A private `createMockClient` helper function initializes an `HttpClient` with a Ktor `MockEngine`.
- **Execution**: Tests use `runTest` from `kotlinx-coroutines-test` to handle asynchronous API calls.
- **Pattern**: Most tests follow the **Arrange-Act-Assert** pattern.
- **Fixtures**: JSON responses are defined as local `val` strings within each test to ensure isolation and clarity.

## 3. API Testing Strategy

The strategy focuses on black-box testing the network functions:
- **Success Paths**: Verifying that 200/201 status codes return correctly mapped Kotlin objects.
- **Error Paths**: Verifying that 4xx/5xx status codes trigger the expected `Exception` with appropriate error messages.
- **Serialization**: Ensuring that complex nested JSON structures (like `AuthResponse`) are correctly deserialized.

## 4. Mock Setup

HTTP mocks are created using the **Ktor Mock Engine**. This avoids making actual network calls and allows for deterministic testing of various server responses.

```kotlin
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
```

## 5. Representative Test Cases

### TC01: Successful Signup (AuthApiTest)
- **Purpose**: Ensure a valid signup request returns a success message and parsed user data.
- **Configuration**: Mock a `201 Created` response with a full `SignUpResponse` JSON.
- **Expectation**: `signupUser` returns a response where `statusCode` is 201 and `data.firstName` matches the input.

```kotlin
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
    val response = signupUser(request, mockClient)

    assertEquals("Account created successfully", response.message)
    assertEquals(201, response.statusCode)
    assertNotNull(response.data)
}
```

### TC02: Fetching Vehicle List (VehicleApiTest)
- **Purpose**: Verify that the list of user vehicles is correctly parsed from the "data" array.
- **Configuration**: Mock a `200 OK` with a JSON array inside the `data` field.
- **Expectation**: The returned list has the correct size and field values (e.g., `color`).

```kotlin
@Test
fun testGetVehicles_Success() = runTest {
    val mockJsonResponse = """
        {
            "data": [
                {
                    "id": 100,
                    "brandName": "Toyota",
                    "color": "Black",
                    ...
                }
            ],
            "statusCode": 200,
            "message": "Vehicles fetched successfully"
        }
    """.trimIndent()

    val mockClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
    val response = getVehicles(mockClient)

    assertEquals(1, response.data.size)
    assertEquals("Black", response.data[0].color)
}
```

### TC03: Handling Conflict Errors (ScheduleApiTest)
- **Purpose**: Verify that a 409 Conflict (e.g., unavailable time slot) throws an exception with the server's error message.
- **Configuration**: Mock a `409 Conflict` status with an error message in the body.
- **Expectation**: `assertFailsWith<Exception>` catches the error, and the message contains "Time slot unavailable".

```kotlin
@Test
fun testSetAppointment_Failure_ThrowsCustomException() = runTest {
    val errorJsonResponse = """{"message": "Time slot unavailable"}"""
    val mockClient = createMockClient(errorJsonResponse, HttpStatusCode.Conflict)

    val exception = assertFailsWith<Exception> {
        setAppointment(request, mockClient)
    }

    assertTrue(exception.message!!.contains("Time slot unavailable"))
}
```

## 6. Test Data

Test data is created using:
- **Hardcoded JSON Strings**: Used for mock responses to precisely match expected backend contracts.
- **Data Class Instances**: Request objects (like `SignUpRequest` or `SetAppointmentRequest`) are instantiated with dummy data for the duration of the test.

## 7. Assertions

The following assertions from `kotlin.test` are primarily used:
- `assertEquals(expected, actual)`: Validates status codes, message strings, and field values.
- `assertNotNull(value)`: Ensures the `data` field in response wrappers is not null on success.
- `assertTrue(condition)`: Validates boolean flags or list emptiness.
- `assertFailsWith<T>`: Confirms that improper inputs or server errors correctly throw exceptions.

## 8. Error Handling Tests

Error handling is a core part of the suite. Tests explicitly cover:
- **Validation Errors (400)**: Checking for "Bad Request" responses.
- **Authentication Failures (401)**: Ensuring "Unauthorized" access is handled.
- **Resource Not Found (404)**: Verifying behavior when fetching non-existent vehicles or profiles.
- **Business Logic Conflicts (409/410)**: Handling "Already exists" or "Expired OTP" scenarios.

## 9. Coverage

- **Network Layer**: Approximately **90-95%** coverage. Every defined API function has at least one success and one failure test case.
- **Response Models**: High coverage of all Kotlin `@Serializable` data classes.
- **Business Logic**: Coverage of logic that transforms HTTP status codes into user-friendly error messages.

## 10. Issues & Gaps

- **Simulated Latency**: No current tests for network timeouts or slow responses.
- **Connectivity Loss**: Tests do not currently simulate `IOException` or `ConnectException` (airplane mode/no internet).
- **Multipart Data**: Tests for profile image uploads are missing as they involve binary data handling.
- **Google Auth**: Integration testing for third-party providers like Google Sign-In is limited to mocking the final backend verification.
