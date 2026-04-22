package com.example.carware

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.carware.di.appModule
import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class CarwareApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Add this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel_v3",
                "General Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val sharedPrefs = getSharedPreferences("carware_prefs", MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPrefs)
        val preferencesManager = PreferencesManager(settings)

        startKoin {
            androidContext(this@CarwareApplication)
            modules(appModule(preferencesManager))
        }
    }
}