package com.example.diaryapp.presentation.ui.uiState

data class ResetPasswordUiState(
    val userId: String = "",
    val newPw: String = "",
    val confirmPw: String = "",
    val isLoading: Boolean = false,
    val pwError: String? = null,
    val confirmPwError: String? = null,
)
