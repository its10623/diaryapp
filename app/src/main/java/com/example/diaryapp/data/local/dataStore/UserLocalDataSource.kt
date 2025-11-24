package com.example.diaryapp.data.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.diaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.usersDataStore: DataStore<List<User>> by dataStore(
    fileName = "users.json",
    serializer = UserPreferencesSerializer
)

val Context.settingsDataStore by preferencesDataStore("settings")

object UserKeys {
    val USER_ID = stringPreferencesKey("user_id")
    val PASSWORD_HASH = stringPreferencesKey("password_hash")
    val AUTO_LOGIN = booleanPreferencesKey("auto_login")
}

class UserLocalDataSource @Inject constructor(
    private val context: Context
) {

    fun getAllUsers(): Flow<List<User>> =
        context.usersDataStore.data

    suspend fun findUser(userName: String): User? =
        context.usersDataStore.data.first().find { it.userName == userName }

    suspend fun register(user: User) {
        context.usersDataStore.updateData { users ->
            users + user
        }
    }

    suspend fun updateUser(updated: User) {
        context.usersDataStore.updateData { users ->
            users.map { if (it.userName == updated.userName) updated else it }
        }
    }

    suspend fun saveUserId(id: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[UserKeys.USER_ID] = id
        }
    }

    fun getSavedUserId(): Flow<String?> =
        context.settingsDataStore.data.map { it[UserKeys.USER_ID] }

    fun getPasswordHash(): Flow<String?> =
        context.settingsDataStore.data.map {
            it[UserKeys.PASSWORD_HASH]
        }

    suspend fun savePasswordHash(hash: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[UserKeys.PASSWORD_HASH] = hash
        }
    }

    fun getAutoLogin(): Flow<Boolean> =
        context.settingsDataStore.data.map { prefs ->
            prefs[UserKeys.AUTO_LOGIN] ?: false
        }

    suspend fun saveAutoLogin(value: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[UserKeys.AUTO_LOGIN] = value
        }
    }

    suspend fun clearSettings() {
        context.settingsDataStore.edit { it.clear() }
    }

    suspend fun clearAllUsers() {
        context.usersDataStore.updateData { emptyList() }
    }

}