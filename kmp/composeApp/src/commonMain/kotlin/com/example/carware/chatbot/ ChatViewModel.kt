package com.example.carware.chatbot

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatMessage(val role: String, val content: String)

@Serializable
data class ChatRequest(
    val model: String,
    val max_tokens: Int,
    val messages: List<ChatMessage>
)

@Serializable
data class ContentBlock(val type: String, val text: String)

@Serializable
data class ChatResponse(val content: List<ContentBlock>)

class ChatViewModel(
    private val httpClient: HttpClient,
    private val scope: CoroutineScope
) {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiKey = "YOUR_API_KEY"

    private val json = Json { ignoreUnknownKeys = true }

    fun sendMessage(userInput: String) {
        val updated = _messages.value + ChatMessage("user", userInput)
        _messages.value = updated
        _isLoading.value = true

        scope.launch {
            try {
                val response: ChatResponse = httpClient.post("https://api.anthropic.com/v1/messages") {
                    header("x-api-key", apiKey)
                    header("anthropic-version", "2023-06-01")
                    contentType(ContentType.Application.Json)
                    setBody(ChatRequest(
                        model = "claude-sonnet-4-20250514",
                        max_tokens = 1024,
                        messages = updated
                    ))
                }.body()

                val reply = response.content.firstOrNull()?.text ?: "No response"
                _messages.value = _messages.value + ChatMessage("assistant", reply)
            } catch (e: Exception) {
                _messages.value = _messages.value + ChatMessage("assistant", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearChat() { _messages.value = emptyList() }
}