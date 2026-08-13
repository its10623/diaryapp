package com.example.diaryapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.domain.usecase.user.AutoLoginUseCase
import com.example.diaryapp.domain.usecase.user.LoginUseCase
import com.example.diaryapp.domain.repository.UserRepository
import com.example.diaryapp.presentation.ui.event.LoginEvent
import com.example.diaryapp.presentation.ui.uiState.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val autoLoginUseCase: AutoLoginUseCase,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<LoginEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var uiState by mutableStateOf(LoginUiState())
        private set

    val autoLogin = autoLoginUseCase.getAutoLogin().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val savedUserId = userRepository.getSavedUserId().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    val savedPasswordHash = userRepository.getSavedPasswordHash().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    fun loginWithGoogle(email: String, idToken: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)

                userRepository.saveGoogleLoginInfo(
                    email = email,
                    idToken = idToken
                )

                _uiEvent.emit(LoginEvent.LoginSuccess(email))
            } catch (e: Exception) {
                _uiEvent.emit(
                    LoginEvent.LoginFailed("구글 로그인에 실패했습니다.")
                )
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }


    fun onIdChange(value: String) {
        uiState = uiState.copy(userName = value, idError = null)
    }

    fun onPwChange(value: String) {
        uiState = uiState.copy(password = value, pwError = null)
    }

    fun onAutoLoginChange(value: Boolean) {
        viewModelScope.launch {
            uiState = uiState.copy()
            autoLoginUseCase.setAutoLogin(value)
        }
    }

    suspend fun tryAutoLogin() {
        try {
            val isLoggedIn = userRepository.getLoginStatus().first()
            val loginType = userRepository.getLoginType().first()
            val email = userRepository.getUserEmail().first()

            if (isLoggedIn && loginType == "GOOGLE" && !email.isNullOrBlank()) {
                _uiEvent.emit(LoginEvent.LoginSuccess(email))
                return
            }

            val id = savedUserId.value
            val hash = savedPasswordHash.value

            if (!id.isNullOrBlank() && !hash.isNullOrBlank()) {
                val user = userRepository.findUser(id)
                if (user != null && user.password == hash) {
                    _uiEvent.emit(LoginEvent.LoginSuccess(id))
                    return
                }
            }

            // 자동 로그인 실패 시 로그인 화면 표시
            uiState = uiState.copy(isCheckingAutoLogin = false)
        } catch (e: Exception) {
            uiState = uiState.copy(isCheckingAutoLogin = false)
            _uiEvent.emit(LoginEvent.LoginFailed("자동 로그인 확인에 실패했습니다."))
        }
    }

    fun login() {
        val id = uiState.userName
        val pw = uiState.password

        if (id.isBlank()) {
            uiState = uiState.copy(idError = "아이디를 입력해주세요")
            return
        }
        if (id.length !in 4..20) {
            uiState = uiState.copy(idError = "아이디는 4~20자여야 합니다.")
            return
        }

        if (!id.all { it.isLetterOrDigit() }) {
            uiState = uiState.copy(idError = "특수문자는 사용할 수 없습니다.")
            return
        }

        if (pw.length < 8) {
            uiState = uiState.copy(pwError = "비밀번호는 8자리 이상이어야 합니다.")
            return
        }

        if (
            !pw.any { it.isDigit() } ||
            !pw.any { it.isLetter() } ||
            !pw.any { !it.isLetterOrDigit() }
        ) {
            uiState = uiState.copy(pwError = "영문 + 숫자 + 특수문자를 포함해야 합니다.")
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val success = loginUseCase(id, pw)

            uiState = uiState.copy(isLoading = false)
            if (success) {
                val user = userRepository.findUser(id)!!
                autoLoginUseCase.saveUserId(id)
                autoLoginUseCase.savePasswordHash(user.password)

                _uiEvent.emit(LoginEvent.LoginSuccess(id))
            } else {
                _uiEvent.emit(LoginEvent.LoginFailed("아이디 또는 비밀번호가 올바르지 않습니다."))
            }
        }
    }

    suspend fun logout() {
        autoLoginUseCase.setAutoLogin(false)
        autoLoginUseCase.saveUserId(null)
        autoLoginUseCase.savePasswordHash(null)
        userRepository.clearLoginInfo()
    }
}