package ru.tumist.surfgallery.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.Exception

class LocalDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_KEY)
    suspend fun get(key: String): String? {
        return context.dataStore.data
            .map { prefs -> prefs[stringPreferencesKey(key)] }
            .first()
    }

    suspend fun set(key: String, value: String): Boolean {
        return try {
            context.dataStore.edit { store ->
                store[stringPreferencesKey(key)] = value
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun removeEntry(key: String) {
       context.dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }

    companion object {
        private const val STORE_KEY = "TUMIST_SURF_GALLERY_KEY"
    }

}
