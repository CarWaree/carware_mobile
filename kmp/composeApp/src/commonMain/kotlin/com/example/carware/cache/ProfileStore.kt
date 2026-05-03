package com.example.carware.cache

import com.example.carware.network.cache.ProfileCacheData
import getCacheDirectory
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

val profileStore: KStore<ProfileCacheData> by lazy {
    storeOf(
        file = Path("${getCacheDirectory()}/profile_cache.json"),
        default = ProfileCacheData()
    )
}
