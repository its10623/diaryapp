package com.example.diaryapp.application.validator

interface DiaryValidator {
    fun validateTitleInput(title: String)
    fun validateContentInput(content: String)
}