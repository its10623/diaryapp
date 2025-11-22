package com.example.diaryapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf(FindPasswordUiState())
        private set

    private val _event = MutableSharedFlow<FindPasswordEvent>()
    val event = _event.asSharedFlow()

    fun onIdChange(value: String) {
        uiState = uiState.copy(id = value, idError = null)
    }

    fun checkUserId() {
        viewModelScope.launch {

            val id = uiState.id

            // 1) 입력 검증
            if (id.isBlank()) {
                uiState = uiState.copy(idError = "아이디를 입력해주세요.")
                return@launch
            }

            if (id.length !in 4..20) {
                uiState = uiState.copy(idError = "아이디는 4~20자로 입력해주세요.")
                return@launch
            }

            if (!id.all { it.isLetterOrDigit() }) {
                uiState = uiState.copy(idError = "특수문자는 사용할 수 없습니다.")
                return@launch
            }

            // 2) 로딩 시작
            uiState = uiState.copy(isLoading = true)

            // 3) DB/UserRepository 검증
            val user = repo.findUser(id)

            uiState = uiState.copy(isLoading = false)

            // 4) 결과 처리
            if (user != null) {
                _event.emit(FindPasswordEvent.Success(id))
            } else {
                uiState = uiState.copy(idError = "존재하지 않는 아이디입니다.")
            }
        }
    }
}

data class FindPasswordUiState(
    val id: String = "",
    val isLoading: Boolean = false,
    val idError: String? = null
)

sealed class FindPasswordEvent {
    data class Success(val id: String) : FindPasswordEvent()
    data class Fail(val message: String) : FindPasswordEvent()
}