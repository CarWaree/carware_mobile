package com.example.carware.util.lang

class LocalizedStrings(private val currentLanguage: AppLanguage) {

    private val stringsMap
        get() = if (currentLanguage == AppLanguage.AR) StringsAr else StringsEn

    fun get(key: String): String {
        return stringsMap[key] ?: key // fallback to key itself if missing
    }
}
