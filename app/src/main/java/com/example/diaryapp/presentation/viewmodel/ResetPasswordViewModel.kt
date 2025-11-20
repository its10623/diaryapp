package com.example.diaryapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.application.usecase.user.FindPasswordUseCase
import com.example.diaryapp.application.usecase.user.ResetPasswordResult
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: FindPasswordUseCase
) : ViewModel() {

    var uiState by mutableStateOf(ResetPasswordUiState())
        private set

    private val _event = MutableSharedFlow<ResetPasswordUiEvent>()
    val event = _event.asSharedFlow()

    fun onIdChange(value: String) {
        uiState = uiState.copy(userId = value)
    }

    fun onPwChange(value: String) {
        uiState = uiState.copy(newPw = value)
    }

    fun onConfirmPwChange(value: String) {
        uiState = uiState.copy(confirmPw = value)
    }

    fun resetPassword() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val result = resetPasswordUseCase(
                uiState.userId,
                uiState.newPw,
                uiState.confirmPw
            )

            uiState = uiState.copy(isLoading = false)

            when (result) {
                is ResetPasswordResult.Success ->
                    _event.emit(ResetPasswordUiEvent.Success)

                is ResetPasswordResult.Fail ->
                    _event.emit(ResetPasswordUiEvent.Fail(result.message))
            }
        }
    }
}

data class ResetPasswordUiState(
    val userId: String = "",
    val newPw: String = "",
    val confirmPw: String = "",
    val isLoading: Boolean = false
)

sealed class ResetPasswordUiEvent {
    object Success : ResetPasswordUiEvent()
    data class Fail(val message: String) : ResetPasswordUiEvent()
}