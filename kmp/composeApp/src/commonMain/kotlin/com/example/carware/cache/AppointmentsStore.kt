package com.example.carware.cache

import com.example.carware.network.cacheData.AppointmentsCachedData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path



val appointmentsStore: KStore<AppointmentsCachedData>by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/appointments_cache.json"),
        default = AppointmentsCachedData()
    )
}

