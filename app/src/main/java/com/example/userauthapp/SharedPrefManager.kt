package com.example.userauthapp

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {
    private const val PREF_NAME = "AuthPrefs"
    private const val KEY_TOKEN = "token"

    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        getSharedPrefs(context).edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPrefs(context).getString(KEY_TOKEN, null)
    }

    fun clearToken(context: Context) {
        getSharedPrefs(context).edit().remove(KEY_TOKEN).apply()
    }
}