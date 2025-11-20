package com.example.diaryapp.presentation.viewmodel

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

    val autoLogin = autoLoginUseCase.get().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun login(userName: String, password: String, autoLoginWanted: Boolean) {
        viewModelScope.launch {
            val success = loginUseCase(userName, password)
            if (success) {
                autoLoginUseCase.set(autoLoginWanted)
                _uiEvent.emit(LoginUiEvent.LoginSuccess)
            } else {
                _uiEvent.emit(LoginUiEvent.LoginFailed("아이디 또는 비밀번호가 올바르지 않습니다."))
            }
        }
    }
}

sealed class LoginUiEvent {
    object LoginSuccess : LoginUiEvent()
    data class LoginFailed(val message: String) : LoginUiEvent()
}