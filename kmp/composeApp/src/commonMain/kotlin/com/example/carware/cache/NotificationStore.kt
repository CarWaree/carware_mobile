package com.example.carware.cache

import com.example.carware.network.cache.NotificationCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

val notificationStore: KStore<NotificationCacheData> by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/notification_cache.json"),
        default = NotificationCacheData()
    )
}
