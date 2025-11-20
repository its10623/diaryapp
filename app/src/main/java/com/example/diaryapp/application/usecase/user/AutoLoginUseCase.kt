package com.example.diaryapp.application.usecase.user

import com.example.diaryapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class AutoLoginUseCase @Inject constructor(
    private val repo: UserRepository
) {
    fun get(): Flow<Boolean> = repo.getAutoLogin()
    suspend fun set(value: Boolean) = repo.setAutoLogin(value)
}