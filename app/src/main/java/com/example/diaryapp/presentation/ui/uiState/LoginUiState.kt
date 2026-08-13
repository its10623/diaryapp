package com.example.diaryapp.presentation.ui.uiState

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val idError: String? = null,
    val pwError: String? = null,
    val isLoading: Boolean = false,
    val isCheckingAutoLogin: Boolean = true
)