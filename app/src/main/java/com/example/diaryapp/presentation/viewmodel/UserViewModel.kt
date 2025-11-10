package com.example.diaryapp.presentation.viewmodel

import com.example.diaryapp.dto.UserDto

interface UserViewModel {
    fun onSignUp(userDto: UserDto): Boolean
    fun onLogin(userDto: UserDto): UserDto?
}