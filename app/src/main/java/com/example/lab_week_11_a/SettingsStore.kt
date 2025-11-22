package com.example.lab_week_11_a

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val USER_TEXT_KEY = stringPreferencesKey("user_text")
    }

    val userTextFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TEXT_KEY] ?: ""
        }

    suspend fun saveUserText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TEXT_KEY] = text
        }
    }
}
