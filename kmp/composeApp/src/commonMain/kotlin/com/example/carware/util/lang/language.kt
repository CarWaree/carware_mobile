package com.example.carware.util.lang


enum class AppLanguage(val isoCode: String) {
    EN("en"),
    AR("ar");

    companion object {
        fun fromCode(code: String?): AppLanguage {
            return entries.find { it.isoCode == code } ?: EN // Default to English
        }
    }
}