package com.example.carware.util


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (ByteArray?) -> Unit
): ImagePickerLauncher {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val bytes = uri?.let {
            context.contentResolver.openInputStream(it)?.use { stream -> stream.readBytes() }
        }
        onResult(bytes)
    }

    // Return an anonymous object implementing your interface
    return remember {
        object : ImagePickerLauncher {
            override fun launch() = launcher.launch("image/*")
        }
    }
}