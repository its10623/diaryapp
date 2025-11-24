package com.example.diaryapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.domain.usecase.user.FindPasswordUseCase
import com.example.diaryapp.domain.usecase.user.ResetPasswordResult
import com.example.diaryapp.presentation.ui.event.ResetPasswordEvent
import com.example.diaryapp.presentation.ui.uiState.ResetPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: FindPasswordUseCase
) : ViewModel() {

    var uiState by mutableStateOf(ResetPasswordUiState())
        private set

    private val _event = MutableSharedFlow<ResetPasswordEvent>()
    val event = _event.asSharedFlow()

    fun initUserId(userName: String) {
        uiState = uiState.copy(userId = userName, pwError = null)
    }

    fun onPwChange(value: String) {
        uiState = uiState.copy(newPw = value, confirmPwError = null)
    }

    fun onConfirmPwChange(value: String) {
        uiState = uiState.copy(confirmPw = value)
    }

    fun resetPassword() {
        val pw = uiState.newPw
        val cpw = uiState.confirmPw

        if (pw.length !in 8..16) {
            uiState = uiState.copy(pwError = "비밀번호는 8~16자여야 합니다.")
            return
        }

        if (!pw.any { it.isDigit() } ||
            !pw.any { it.isLetter() } ||
            !pw.any { !it.isLetterOrDigit() }) {

            uiState = uiState.copy(pwError = "영문, 숫자, 특수문자를 포함해야 합니다.")
            return
        }

        if (pw != cpw) {
            uiState = uiState.copy(confirmPwError = "비밀번호가 일치하지 않습니다.")
            return
        }
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
                    _event.emit(ResetPasswordEvent.Success)

                is ResetPasswordResult.Fail ->
                    _event.emit(ResetPasswordEvent.Fail(result.message))
            }
        }
    }
}