package com.example.carware.cache

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun createImageLoader(context: PlatformContext): ImageLoader =
    ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(
                    (NSSearchPathForDirectoriesInDomains(
                        NSCachesDirectory, NSUserDomainMask, true
                    ).first() as String + "/image_cache").toPath()
                )
                .maxSizeBytes(50 * 1024 * 1024)
                .build()
        }
        .build()