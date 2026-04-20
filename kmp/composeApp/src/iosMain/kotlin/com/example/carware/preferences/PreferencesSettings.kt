package com.example.carware.preferences

import com.example.carware.util.storage.PreferencesManager
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

// iOS equivalent of CarwareApplication
object CarwareApplicationIOS {
    private val defaults = NSUserDefaults.standardUserDefaults

    class IOSSettings {
        fun getString(key: String): String? = defaults.stringForKey(key)
        fun setString(key: String, value: String?) {
            if (value != null) {
                defaults.setObject(value, key)
            } else {
                defaults.removeObjectForKey(key)
            }
        }
        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return if (defaults.objectForKey(key) != null) {
                defaults.boolForKey(key)
            } else {
                defaultValue
            }
        }
        fun setBoolean(key: String, value: Boolean) {
            defaults.setBool(value, key)
        }
        fun remove(key: String) = defaults.removeObjectForKey(key)
        fun clear() = defaults.removePersistentDomainForName("carware_prefs")
    }

    val preferences = PreferencesManager(Settings())
}