package com.example.diaryapp.domain.validator

interface DiaryValidator {
    fun validateTitleInput(title: String)
    fun validateContentInput(content: String)
}