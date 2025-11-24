package com.example.diaryapp.presentation.ui.uiState

data class RegisterUiState(
    val id: String = "",
    val pw: String = "",
    val confirmPw: String = "",
    val idError: String? = null,
    val pwError: String? = null,
    val confirmPwError: String? = null,
    val isLoading: Boolean = false
)