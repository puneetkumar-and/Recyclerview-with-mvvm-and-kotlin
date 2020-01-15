package com.example.demo.utils

import android.content.SharedPreferences
import com.example.demo.data.models.Repo
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsHelperImpl @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val KEY_Language = "LanguageDetail"
    }

    fun getString(key: String, value: String?): String? = sharedPreferences.getString(key, value)

    fun getBoolean(key: String, default: Boolean): Boolean =
        sharedPreferences.getBoolean(key, default)

    fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }

    fun getRepo(): Repo? {
        return try {
            val json = getString(KEY_Language, null)
            gson.fromJson<Repo>(json, Repo::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun saveRepo(repo: Repo) {
        val repoJson = gson.toJson(repo)
        save(KEY_Language, repoJson)
    }
}