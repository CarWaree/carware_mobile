package com.example.carware.util.storage


import com.example.carware.cache.historyStore
import com.example.carware.cache.notificationStore
import com.example.carware.cache.profileStore
import com.example.carware.cache.reminderStore
import com.example.carware.cache.servicesStore
import com.example.carware.cache.vehiclesStore
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class PreferencesManager(
    private val settings: Settings = Settings()
) {

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"

        private const val KEY_RESET_TOKEN = "reset_token"


        private const val KEY_USER_ID = "user_id"
        private const val KEY_ONBOARDING = "onboarding_complete"
        private const val KEY_CAR_ADDED = "car_added_complete"
        private val LANG_KEY = "selected_language"
        private const val KEY_LANGUAGE_SELECTED = "is_lang_selected"
        private const val KEY_EXPIRES_ON = "expires_on"
        private const val KEY_EMAIL_VERIFIED = "is_email_verified"

        private const val KEY_PRIMARY_CAR_ID = "primary_car_id"

    }

    /* -------------------- Expires On -------------------- */
    fun saveExpiresOn(expiresOn: String?) {
        if (expiresOn != null) {
            settings[KEY_EXPIRES_ON] = expiresOn
        } else {
            settings.remove(KEY_EXPIRES_ON)
        }
    }

    fun getExpiresOn(): String? {
        return settings.getStringOrNull(KEY_EXPIRES_ON)
    }

    /* -------------------- Email Verified -------------------- */
    fun saveEmailVerified(isVerified: Boolean) {
        settings.putBoolean(KEY_EMAIL_VERIFIED, isVerified)
    }

    fun isEmailVerified(): Boolean {
        return settings.getBoolean(KEY_EMAIL_VERIFIED, false)
    }
    /* -------------------- Auth Token -------------------- */

    fun saveToken(token: String?) {
        if (token != null) {
            settings[KEY_AUTH_TOKEN] = token
        } else {
            settings.remove(KEY_AUTH_TOKEN)
        }
    }

    fun saveRefreshToken(token: String?) {
        if (token != null) settings[KEY_REFRESH_TOKEN] = token
        else settings.remove(KEY_REFRESH_TOKEN)
    }

    fun getRefreshToken(): String? = settings.getStringOrNull(KEY_REFRESH_TOKEN)
    fun getToken(): String? {
        return settings.getStringOrNull(KEY_AUTH_TOKEN)
    }

    fun clearToken() {
        settings.remove(KEY_AUTH_TOKEN)
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    /* -------------------- Reset Token -------------------- */
    fun saveResetToken(token: String?) {
        if (token != null) settings[KEY_RESET_TOKEN] = token
        else settings.remove(KEY_RESET_TOKEN)
    }

    fun getResetToken(): String? = settings.getStringOrNull(KEY_RESET_TOKEN)
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

    suspend fun performLogout() {
        clearToken()
        settings.remove(KEY_AUTH_TOKEN)
        settings.remove(KEY_USER_ID)
        settings.remove(KEY_EXPIRES_ON)
        settings.remove(KEY_EMAIL_VERIFIED)
        settings.remove(KEY_RESET_TOKEN)
        settings.remove(KEY_CAR_ADDED)
        settings.remove(KEY_PRIMARY_CAR_ID)  // ← Add this

        historyStore.reset()
        notificationStore.reset()
        profileStore.reset()
        reminderStore.reset()
        servicesStore.reset()
        vehiclesStore.reset()




        // Add any other user-specific keys here

    }

    /* ------------------language ---------------*/
    fun setLanguageSelected(isComplete: Boolean) {
        settings.putBoolean(KEY_LANGUAGE_SELECTED, isComplete)
    }


    fun isLanguageSelected(): Boolean {
        return settings.getBoolean(KEY_LANGUAGE_SELECTED, false)
    }

    /* ------------------ language Code ------------------*/

    fun getLanguageCode(): String {
        return settings.getString(LANG_KEY, "en")
    }

    fun saveLanguageCode(code: String) {
        settings.putString(LANG_KEY, code)
    }


    /* -------------------- Primary Car -------------------- */

    fun setPrimaryCarId(carId: Int?) {
        if (carId != null) {
            settings[KEY_PRIMARY_CAR_ID] = carId
        } else {
            settings.remove(KEY_PRIMARY_CAR_ID)
        }
    }

    fun getPrimaryCarId(): Int {
        return settings.getInt(KEY_PRIMARY_CAR_ID, 0)  // Default to 0
    }
}