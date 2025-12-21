package com.example.carware.util.lang

import com.example.carware.util.storage.PreferencesManager

class LocalizedStrings(private val prefs: PreferencesManager) {


    private val currentLanguage: AppLanguage
        get() = AppLanguage.fromCode(prefs.getLanguageCode())
    private val stringsMap
        get() = if (currentLanguage == AppLanguage.AR) StringsAr else StringsEn

    fun get(key: String): String {
        return stringsMap[key] ?: key
    }
}
