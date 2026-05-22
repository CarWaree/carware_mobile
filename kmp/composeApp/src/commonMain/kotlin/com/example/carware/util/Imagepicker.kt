package com.example.carware.util



import androidx.compose.runtime.Composable

@Composable
expect fun rememberImagePickerLauncher(onResult: (ByteArray?) -> Unit): ImagePickerLauncher

interface ImagePickerLauncher {
    fun launch()
}