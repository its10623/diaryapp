package com.example.diaryapp.application.usecase.user

import com.example.diaryapp.domain.repository.UserRepository
import com.example.diaryapp.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(userName: String, password: String): Boolean {

        if (userName.isBlank()) return false
        if (password.isBlank()) return false

        return repo.login(userName,password)
    }
}