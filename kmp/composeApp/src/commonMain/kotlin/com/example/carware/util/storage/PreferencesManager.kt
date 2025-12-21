package com.example.carware.util.storage


import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.russhwolf.settings.get

class PreferencesManager(
    private val settings: Settings = Settings()
) {

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ONBOARDING = "onboarding_complete"
        private const val KEY_CAR_ADDED = "car_added_complete"
        private val LANG_KEY = "selected_language"
    }

    /* -------------------- Auth Token -------------------- */

    fun saveToken(token: String?) {
        if (token != null) {
            settings[KEY_AUTH_TOKEN] = token
        } else {
            settings.remove(KEY_AUTH_TOKEN)
        }
    }

    fun getToken(): String? {
        return settings.getStringOrNull(KEY_AUTH_TOKEN)
    }

    fun clearToken() {
        settings.remove(KEY_AUTH_TOKEN)
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    /* -------------------- User ID -------------------- */

    fun saveUserId(userId: String?) {
        if (userId != null) {
            settings[KEY_USER_ID] = userId
        } else {
            settings.remove(KEY_USER_ID)
        }
    }

    fun getUserId(): String? {
        return settings.getStringOrNull(KEY_USER_ID)
    }

    /* -------------------- Onboarding -------------------- */

    fun isOnboardingComplete(): Boolean {
        return settings.getBoolean(KEY_ONBOARDING, false)
    }

    fun setOnboardingComplete() {
        settings.putBoolean(KEY_ONBOARDING, true)
    }

    /* -------------------- Car Added -------------------- */

    fun hasAddedCar(): Boolean {
        return settings.getBoolean(KEY_CAR_ADDED, false)
    }

    fun setCarAdded(isAdded: Boolean) {
        settings.putBoolean(KEY_CAR_ADDED, isAdded)
    }

    /* -------------------- Login/Logout -------------------- */

    fun performLogin(token: String?, userId: String? = null) {
        saveToken(token)
        userId?.let { saveUserId(it) }
    }

    fun performLogout() {
        clearToken()
        settings.remove(KEY_USER_ID)
    }

    /* ------------------language ---------------*/
    fun getLanguageCode(): String {
        return settings.getString(LANG_KEY, "en")
    }

    fun saveLanguageCode(code: String) {
        settings.putString(LANG_KEY, code)
    }

    fun clearAll() {
        settings.clear()
    }
}