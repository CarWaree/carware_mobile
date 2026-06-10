package com.example.carware.chatbot

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.failed
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
expect fun KalamnaWebView(
    modifier: Modifier,
    jsCommand: String?,
    onCommandConsumed: () -> Unit,
    onRequestClose: () -> Unit
)

// expect function so each platform returns its own screen height
@Composable
expect fun getScreenHeight(): Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotSheet(
    onDismiss: () -> Unit,
    viewModel: ChatViewModel = koinInject()
) {
    val pendingJsCommand by viewModel.pendingJsCommand.collectAsState()
    val sheetContentHeight = getScreenHeight() * 0.90f

    ModalBottomSheet(
        onDismissRequest = {
            println("[ChatBotSheet] dismissed")
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    println("[ChatBotSheet] close button tapped")
                    onDismiss()
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.failed),
                        contentDescription = "Close chat"
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetContentHeight)
        ) {
            KalamnaWebView(
                modifier = Modifier.fillMaxSize(),
                jsCommand = pendingJsCommand,
                onCommandConsumed = {
                    println("[ChatBotSheet] command consumed")
                    viewModel.clearPendingCommand()
                },
                onRequestClose = {
                    println("[ChatBotSheet] requestClose from widget → dismissing")
                    onDismiss()
                }
            )
        }
    }
}