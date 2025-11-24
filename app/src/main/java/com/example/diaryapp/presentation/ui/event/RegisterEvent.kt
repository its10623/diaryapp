package com.example.diaryapp.presentation.ui.event

sealed class RegisterEvent {
    object Success : RegisterEvent()
    data class Fail(val message: String) : RegisterEvent()
}