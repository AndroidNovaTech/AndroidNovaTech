package com.zain.giftcard.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(private val context: Context) {

    // Common SharedPreferences instances
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    private val userPrefs: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    private val scratchPrefs: SharedPreferences =
        context.getSharedPreferences("ScratchCardPrefs", Context.MODE_PRIVATE)

    // -----------------------------
    // User Credentials
    // -----------------------------
    fun saveCredentials(username: String, pin: String, question: String, answer: String) {
        sharedPref.edit()
            .putString("Username", username)
            .putString("PIN", pin)
            .putString("SecurityQuestion", question)
            .putString("SecurityAnswer", answer)
            .apply()
    }

    fun getUsername(): String? = sharedPref.getString("Username", null)
    fun getPin(): String? = sharedPref.getString("PIN", null)
    fun getSecurityQuestion(): String? = sharedPref.getString("SecurityQuestion", null)
    fun getSecurityAnswer(): String? = sharedPref.getString("SecurityAnswer", null)

    // -----------------------------
    // Disclaimer Status
    // -----------------------------
    fun setDisclaimerCompleted() {
        sharedPref.edit().putBoolean("DISCLAIMER_COMPLETED", true).apply()
    }

    fun isDisclaimerCompleted(): Boolean {
        return sharedPref.getBoolean("DISCLAIMER_COMPLETED", false)
    }

    // -----------------------------
    // PIN Reset
    // -----------------------------
    fun resetPin(newPin: String) {
        sharedPref.edit().putString("PIN", newPin).apply()
    }

    // -----------------------------
    // Scratch Count
    // -----------------------------
    fun getScratchCount(): Int = userPrefs.getInt("scratch_count", 0)

    fun incrementScratchCount() {
        val newCount = getScratchCount() + 1
        userPrefs.edit().putInt("scratch_count", newCount).apply()
    }

    fun resetScratchCountDaily() {
        userPrefs.edit().putInt("scratch_count", 0).apply()
    }

    // -----------------------------
    // Scratch Card Timing (24 hour expiry)
    // -----------------------------
    fun saveScratchTime(cardId: String) {
        val currentTime = System.currentTimeMillis()
        scratchPrefs.edit().putLong(cardId, currentTime).apply()
    }

    fun isCardExpired(cardId: String): Boolean {
        val lastScratchTime = scratchPrefs.getLong(cardId, 0)
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastScratchTime) > (24 * 60 * 60 * 1000) // 24 hours in milliseconds
    }

    fun clearExpiredCard(cardId: String) {
        scratchPrefs.edit().remove(cardId).apply()
    }
}


