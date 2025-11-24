package com.example.diaryapp.presentation.ui.event

sealed class ResetPasswordEvent {
    object Success : ResetPasswordEvent()
    data class Fail(val message: String) : ResetPasswordEvent()
}