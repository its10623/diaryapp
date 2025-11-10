package com.example.diaryapp.application.usecase

import com.example.diaryapp.dto.UserDto

interface UserUseCase {
    fun signUpUseCase(userDto: UserDto)
    fun loginUseCase(userDto: UserDto): UserDto
}