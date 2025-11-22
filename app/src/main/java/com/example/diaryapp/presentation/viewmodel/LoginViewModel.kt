package com.example.diaryapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.application.usecase.user.AutoLoginUseCase
import com.example.diaryapp.application.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var uiState by mutableStateOf(LoginUiState())
        private set

    val autoLogin = autoLoginUseCase.get().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun onIdChange(value: String) {
        uiState = uiState.copy(userName = value, idError = null)
    }

    fun onPwChange(value: String) {
        uiState = uiState.copy(password = value, pwError = null)
    }

    fun onAutoLoginChange(value: Boolean) {
        uiState = uiState.copy(autoLogin = value)
    }

    fun login() {
        val id = uiState.userName
        val pw = uiState.password

        // 아이디 검증
        if (id.isBlank()) {
            uiState = uiState.copy(idError = "아이디를 입력해주세요")
            return
        }
        // 길이 검증
        if (id.length !in 4..20) {
            uiState = uiState.copy(idError = "아이디는 4~20자여야 합니다.")
            return
        }

        // 특수문자 검증
        if (!id.all { it.isLetterOrDigit() }) {
            uiState = uiState.copy(idError = "특수문자는 사용할 수 없습니다.")
            return
        }

        // 비밀번호 검증
        if (pw.length < 8) {
            uiState = uiState.copy(pwError = "비밀번호는 8자리 이상이어야 합니다.")
            return
        }

        if (!pw.any { it.isDigit() } || !pw.any { it.isLetter() } || !pw.any() { it.isLetterOrDigit() }) {
            uiState = uiState.copy(pwError = "영문 + 숫자를 포함해야 합니다.")
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val success = loginUseCase(uiState.userName, uiState.password)

            uiState = uiState.copy(isLoading = false)
            if (success) {
                autoLoginUseCase.set(uiState.autoLogin)
                _uiEvent.emit(LoginUiEvent.LoginSuccess(id))
            } else {
                _uiEvent.emit(LoginUiEvent.LoginFailed("아이디 또는 비밀번호가 올바르지 않습니다."))
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            autoLoginUseCase.set(false)
        }
    }
}

sealed class LoginUiEvent {
    data class LoginSuccess(val userName: String) : LoginUiEvent()
    data class LoginFailed(val message: String) : LoginUiEvent()
}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val autoLogin: Boolean = false,
    val idError: String? = null,
    val pwError: String? = null,
    val isLoading: Boolean = false
)