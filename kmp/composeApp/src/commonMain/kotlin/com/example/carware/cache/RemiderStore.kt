package com.example.carware.cache

import com.example.carware.network.cache.ReminderCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

val reminderStore: KStore<ReminderCacheData> by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/reminder_cache.json"),
        default = ReminderCacheData()
    )
}
