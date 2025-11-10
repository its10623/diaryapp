package com.example.diaryapp.presentation.viewmodel

import com.example.diaryapp.application.usecase.UserUseCase
import com.example.diaryapp.dto.UserDto

class UserViewModelImpl(
    private val userUseCase: UserUseCase,
) : UserViewModel {

    override fun onSignUp(userDto: UserDto): Boolean {
        return try {
            userUseCase.signUpUseCase(userDto)
            println("회원가입이 완료되었습니다.")
            true
        } catch (e: Exception) {
            println("회원가입 실패: ${e.message}")
            false
        }
    }

    override fun onLogin(userDto: UserDto): UserDto? {
        return try {
            val loggedInUser = userUseCase.loginUseCase(userDto)
            println("로그인 성공")
            loggedInUser
        } catch (e: Exception) {
            println("로그인 실패: ${e.message}")
            null
        }
    }
}