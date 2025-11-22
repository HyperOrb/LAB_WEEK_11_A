package com.example.lab_week_11_a

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class SettingsViewModel(private val settingsStore: SettingsStore) : ViewModel() {

    val userTextFlow: Flow<String> = settingsStore.userTextFlow

    fun saveText(text: String) {
        viewModelScope.launch {
            settingsStore.saveUserText(text)
        }
    }
}
