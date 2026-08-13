package com.example.diaryapp.presentation.ui.event

sealed class LoginEvent {
    data class LoginSuccess(val currentUserKey: String) : LoginEvent()
    data class LoginFailed(val message: String) : LoginEvent()
}