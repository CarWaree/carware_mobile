package com.example.carware.cache

import com.example.carware.network.cache.ServicesCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path


val servicesStore: KStore<ServicesCacheData> by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/services_cache.json"),
        default = ServicesCacheData()
    )
}