package com.example.carware.cache

import com.example.carware.network.cache.HistoryCacheData
import com.example.carware.network.cache.ProfileCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

val historyStore: KStore<HistoryCacheData> by lazy{
    storeOf(
        file = Path("${getCacheDirectory()}/history_cache.json"),
        default = HistoryCacheData()

    )

}