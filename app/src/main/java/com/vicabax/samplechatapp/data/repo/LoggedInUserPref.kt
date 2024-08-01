package com.vicabax.samplechatapp.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vicabax.samplechatapp.HardcodedModels.ALICE
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
private val loggedInUser = stringPreferencesKey("logged_in_user")

class LoggedInUserPref private constructor(private val context: Context) {
    fun get() =
        context.dataStore.data
            .map {
                it[loggedInUser] ?: ALICE.name
            }

    suspend fun set(name: String) {
        context.dataStore.edit { settings ->
            settings[loggedInUser] = name
        }
    }

    companion object {
        fun with(context: Context) = LoggedInUserPref(context)
    }

}
