package com.example.carware

import android.app.Application
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.SharedPreferencesSettings

class CarwareApplication : Application() {

    companion object {
        lateinit var preferences: PreferencesManager
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize preferences
        val sharedPrefs = getSharedPreferences("carware_prefs", MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPrefs)
        preferences = PreferencesManager(settings)
    }
}