package com.example.diaryapp.domain.repository

import com.example.diaryapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun findUser(userName: String): User?
    fun getAllUsers(): Flow<List<User>>
    suspend fun register(userName: String, password: String): Boolean
    suspend fun login(userName: String, password: String): Boolean
    fun getAutoLogin(): Flow<Boolean>
    suspend fun setAutoLogin(value: Boolean)
    fun getSavedUserId(): Flow<String?>
    suspend fun saveUserId(id: String?)
    fun getSavedPasswordHash(): Flow<String?>
    suspend fun savePasswordHash(hash: String?)
    suspend fun updatePassword(userName: String, newPassword: String): Boolean
    suspend fun saveGoogleLoginInfo(email: String, idToken: String)
    fun getLoginStatus(): Flow<Boolean>
    fun getLoginType(): Flow<String?>
    fun getUserEmail(): Flow<String?>
    suspend fun clearLoginInfo()
}