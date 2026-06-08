import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

data class ChatMessage(val role: String, val content: String)

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiKey = "7b3290068dbee94b7e57ce038d541a0e78158ee59b3aea8baa231cc3bf450df0" // 🔑 Replace with your key
    private val client = OkHttpClient()

    fun sendMessage(userInput: String) {
        val updated = _messages.value + ChatMessage("user", userInput)
        _messages.value = updated
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val messagesJson = JSONArray().apply {
                    updated.forEach { msg ->
                        put(JSONObject().apply {
                            put("role", msg.role)
                            put("content", msg.content)
                        })
                    }
                }

                val body = JSONObject().apply {
                    put("model", "claude-sonnet-4-20250514")
                    put("max_tokens", 1024)
                    put("messages", messagesJson)
                }.toString()

                val request = Request.Builder()
                    .url("https://api.anthropic.com/v1/messages")
                    .addHeader("x-api-key", apiKey)
                    .addHeader("anthropic-version", "2023-06-01")
                    .addHeader("content-type", "application/json")
                    .post(body.toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() ?: ""
                val json = JSONObject(responseBody)
                val reply = json
                    .getJSONArray("content")
                    .getJSONObject(0)
                    .getString("text")

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