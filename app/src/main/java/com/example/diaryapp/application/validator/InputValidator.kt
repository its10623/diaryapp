package com.example.diaryapp.application.validator

interface InputValidator {
    fun validateUserName(input: String)
    fun validatePassword(input: String)
}