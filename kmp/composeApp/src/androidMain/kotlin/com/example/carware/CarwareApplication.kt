package com.example.carware

import android.app.Application
import com.example.carware.di.appModule
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.startKoin

class CarwareApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences("carware_prefs", MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPrefs)
        val preferencesManager = PreferencesManager(settings)


        startKoin {
            androidContext(this@CarwareApplication)
            modules(appModule(preferencesManager))
        }
    }
}