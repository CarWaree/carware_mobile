package com.example.carware.chatbot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Use println for KMP-compatible logging (works on both Android and iOS)
private const val TAG = "ChatViewModel"

class ChatViewModel(
    private val scope: CoroutineScope
) {
    private val _isSheetVisible = MutableStateFlow(false)
    val isSheetVisible: StateFlow<Boolean> = _isSheetVisible.asStateFlow()

    private val _pendingJsCommand = MutableStateFlow<String?>(null)
    val pendingJsCommand: StateFlow<String?> = _pendingJsCommand.asStateFlow()

    init {
        println("[$TAG] ViewModel initialized")
    }

    fun openSheet() {
        println("[$TAG] openSheet() called → isSheetVisible = true")
        _isSheetVisible.value = true
    }

    fun closeSheet() {
        println("[$TAG] closeSheet() called → isSheetVisible = false")
        _isSheetVisible.value = false
    }

    fun openWidget() {
        println("[$TAG] openWidget() → queuing JS: window.KalamnaWidgetAPI?.open()")
        _pendingJsCommand.value = "window.KalamnaWidgetAPI?.open()"
    }

    fun setTheme(theme: String) {
        println("[$TAG] setTheme($theme) → queuing JS")
        _pendingJsCommand.value = "window.KalamnaWidgetAPI?.setTheme('$theme')"
    }

    fun setLanguage(language: String) {
        println("[$TAG] setLanguage($language) → queuing JS")
        _pendingJsCommand.value = "window.KalamnaWidgetAPI?.setLanguage('$language')"
    }

    fun clearPendingCommand() {
        println("[$TAG] clearPendingCommand() → command consumed, clearing")
        _pendingJsCommand.value = null
    }
}