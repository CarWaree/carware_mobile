
package com.example.carware.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.PhotosUI.*
import platform.UIKit.UIApplication
import platform.darwin.NSObject

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (ByteArray?) -> Unit
): ImagePickerLauncher {
    val delegate = remember {
        ImagePickerDelegate(onResult)
    }

    return remember {
        object : ImagePickerLauncher {
            override fun launch() {
                val config = PHPickerConfiguration().apply {
                    filter = PHPickerFilter.imagesFilter
                    selectionLimit = 1
                }
                val picker = PHPickerViewController(configuration = config)
                picker.delegate = delegate
                UIApplication.sharedApplication.keyWindow
                    ?.rootViewController
                    ?.presentViewController(picker, animated = true, completion = null)
            }
        }
    }
}

// Pulled out to avoid recreating the NSObject on recomposition
class ImagePickerDelegate(
    private val onResult: (ByteArray?) -> Unit
) : NSObject(), PHPickerViewControllerDelegateProtocol {

    @OptIn(ExperimentalForeignApi::class)
    override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
        picker.dismissViewControllerAnimated(true, null)

        val result = didFinishPicking.firstOrNull() as? PHPickerResult
        if (result == null) { onResult(null); return }

        result.itemProvider.loadDataRepresentationForTypeIdentifier("public.image") { data, _ ->
            onResult(data?.toByteArray())
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val out = ByteArray(length.toInt())
    out.usePinned { platform.posix.memcpy(it.addressOf(0), bytes, length) }
    return out
}