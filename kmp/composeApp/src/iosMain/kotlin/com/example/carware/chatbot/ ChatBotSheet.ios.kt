package com.example.carware.chatbot

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURL
import platform.WebKit.*

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun KalamnaWebView(
    modifier: Modifier,
    jsCommand: String?,
    onCommandConsumed: () -> Unit,
    onRequestClose: () -> Unit
) {
    var webViewRef by remember { mutableStateOf<WKWebView?>(null) }

    // Evaluate pending JS command from the ViewModel
    LaunchedEffect(jsCommand) {
        if (jsCommand != null) {
            webViewRef?.evaluateJavaScript(jsCommand, completionHandler = null)
            onCommandConsumed()
        }
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val config = WKWebViewConfiguration()
            val webView = WKWebView(
                frame = CGRectMake(0.0, 0.0, 0.0, 0.0),
                configuration = config
            )

            // Inject script to forward kalamna:requestClose back to Swift/Kotlin
            val closeScript = WKUserScript(
                source = """
                    window.addEventListener('kalamna:requestClose', function() {
                        window.webkit.messageHandlers.kalamnaClose.postMessage('close');
                    });
                """.trimIndent(),
                injectionTime = WKUserScriptInjectionTimeAtDocumentEnd,
                forMainFrameOnly = true
            )
            config.userContentController.addUserScript(closeScript)

            // Load the embedded HTML with the correct base URL
            val html = buildKalamnaHtml()
            val baseUrl = NSURL.URLWithString("https://widget.kalamna.tech")!!
            webView.loadHTMLString(html, baseURL = baseUrl)

            webViewRef = webView
            webView
        }
    )
}

private fun buildKalamnaHtml(): String = """
    <!doctype html>
    <html lang="ar">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Kalamna</title>
        <style>
            html, body {
                margin: 0;
                padding: 0;
                height: 100%;
                background: transparent;
            }
        </style>
    </head>
    <body>
        <script
            src="https://widget.kalamna.tech/widget.iife.js"
            data-kalamna-api-key="YOUR_API_KEY"
            data-kalamna-mode="embedded"
            data-kalamna-show-embedded-close="true"
            data-kalamna-theme="dark"
            data-kalamna-language="ar">
        </script>
    </body>
    </html>
""".trimIndent()

@Composable
actual fun getScreenHeight(): Dp {
    TODO("Not yet implemented")
}