package com.example.carware.cache

import com.example.carware.network.cache.VehiclesCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path


val vehiclesStore: KStore<VehiclesCacheData> by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/vehicles_cache.json"),
        default = VehiclesCacheData()
    )
}
