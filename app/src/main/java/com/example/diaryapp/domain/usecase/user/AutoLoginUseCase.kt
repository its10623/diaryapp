package com.example.diaryapp.domain.usecase.user

import com.example.diaryapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class AutoLoginUseCase @Inject constructor(
    private val repo: UserRepository
) {
    fun getAutoLogin(): Flow<Boolean> = repo.getAutoLogin()
    suspend fun setAutoLogin(value: Boolean) = repo.setAutoLogin(value)

    fun getSavedUserId(): Flow<String?> = repo.getSavedUserId()
    suspend fun saveUserId(id: String?) = repo.saveUserId(id)

    fun getSavedPasswordHash(): Flow<String?> = repo.getSavedPasswordHash()
    suspend fun savePasswordHash(hash: String?) = repo.savePasswordHash(hash)
}