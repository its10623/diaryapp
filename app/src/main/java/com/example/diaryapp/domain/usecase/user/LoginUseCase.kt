package com.example.diaryapp.domain.usecase.user

import com.example.diaryapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(userName: String, password: String): Boolean {

        if (userName.isBlank()) return false
        if (password.isBlank()) return false

        return repo.login(userName, password)
    }
}