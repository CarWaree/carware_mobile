package com.example.carware

import com.example.carware.util.storage.PreferencesManager

object CarwareApp {
    lateinit var preferences: PreferencesManager
        private set

    fun init(preferencesManager: PreferencesManager) {
        preferences = preferencesManager
    }
}