package com.jjemsboys.socialscience.studyapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey // Boolean Use Karo!
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("jjems_prefs")

class BookmarkManager(private val context: Context) {
    companion object {
        private val SAVED_QA = stringSetPreferencesKey("saved_qa")
        private val COMPLETED_CHAPTERS = stringSetPreferencesKey("completed_chapters")
        private val DARK_MODE = booleanPreferencesKey("dark_mode") // Fix: Boolean Key
    }

    val savedQa: Flow<Set<String>> = context.dataStore.data.map { it[SAVED_QA] ?: emptySet() }
    val completedChapters: Flow<Set<String>> = context.dataStore.data.map { it[COMPLETED_CHAPTERS] ?: emptySet() }
    
    // Fix: Ye ab Boolean? (Nullable) return karega agar key na mile
    val isDarkMode: Flow<Boolean?> = context.dataStore.data.map { it[DARK_MODE] }

    suspend fun toggleSaveQA(qaId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[SAVED_QA] ?: emptySet()
            prefs[SAVED_QA] = if (current.contains(qaId)) current - qaId else current + qaId
        }
    }

    suspend fun toggleCompleteChapter(chapterId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[COMPLETED_CHAPTERS] ?: emptySet()
            prefs[COMPLETED_CHAPTERS] = if (current.contains(chapterId)) current - chapterId else current + chapterId
        }
    }

    // Fix: Seedha boolean toggle karo, clean aur simple
    suspend fun setDarkMode(enable: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE] = enable
        }
    }
}
