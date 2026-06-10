package com.example.carware.chatbot

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

private const val TAG = "KalamnaWebView"

@Composable
actual fun KalamnaWebView(
    modifier: Modifier,
    jsCommand: String?,
    onCommandConsumed: () -> Unit,
    onRequestClose: () -> Unit
) {
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(jsCommand) {
        if (jsCommand != null) {
            Log.d(TAG, "Evaluating JS command: $jsCommand")
            webViewRef?.evaluateJavascript(jsCommand) { result ->
                Log.d(TAG, "JS command result: $result")
            }
            onCommandConsumed()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            Log.d(TAG, "AndroidView factory: creating WebView")

            WebView(context).apply {
                with(settings) {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    allowFileAccess = true           // ✅ needed to load from assets
                    allowContentAccess = true
                    // ✅ allow the asset-loaded page to make HTTPS requests
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    // ✅ allow file:// origin to reach external URLs
                    allowUniversalAccessFromFileURLs = true
                    allowFileAccessFromFileURLs = true
                    // ✅ ensure modern JS APIs work (Shadow DOM, fetch, WebSocket)
                    javaScriptCanOpenWindowsAutomatically = true
                }

                isFocusableInTouchMode = true
                requestFocus()

                Log.d(TAG, "WebView settings applied")

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView?, url: String?,
                        favicon: android.graphics.Bitmap?
                    ) {
                        Log.d(TAG, "onPageStarted: url=$url")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d(TAG, "onPageFinished: url=$url")

                        // Just verify the API loaded — all mounting logic is in kalamna.html
                        view?.evaluateJavascript(
                            "typeof window.KalamnaWidgetAPI"
                        ) { result ->
                            Log.d(TAG, "KalamnaWidgetAPI type: $result")
                        }
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Log.e(TAG, "onReceivedError: url=${request?.url} " +
                                "code=${error?.errorCode} desc=${error?.description}")
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(msg: ConsoleMessage?): Boolean {
                        val level = msg?.messageLevel()?.name ?: "?"
                        Log.d(TAG, "WebConsole [$level] ${msg?.sourceId()}:${msg?.lineNumber()} → ${msg?.message()}")
                        return true
                    }
                }

                addJavascriptInterface(
                    object {
                        @android.webkit.JavascriptInterface
                        fun onRequestClose() {
                            Log.d(TAG, "JavascriptInterface.onRequestClose() called")
                            onRequestClose()
                        }
                    },
                    "Android"
                )

                // ✅ Load from assets using file:// URL instead of loadDataWithBaseURL
                Log.d(TAG, "Loading file:///android_asset/kalamna.html")
                loadUrl("file:///android_asset/kalamna.html")

                webViewRef = this
            }
        }
    )
}
@Composable
actual fun getScreenHeight(): Dp {
    return LocalConfiguration.current.screenHeightDp.dp
}