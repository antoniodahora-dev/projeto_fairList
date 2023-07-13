package co.a3tecnology.fairlist

import android.app.Application
import android.content.Context

private const val KEY_PREFERENCS = "farList_pref"

private const val KEY_TOKEN = "farList_token"

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val preferences by lazy {
            instance.getSharedPreferences(KEY_PREFERENCS, Context.MODE_PRIVATE)
        }

        fun saveToken(token: String) {
            preferences.edit()
                .putString(KEY_TOKEN, token)
                .apply()
        }

        fun getToken() = preferences.getString(KEY_TOKEN, null)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}