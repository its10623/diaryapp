package com.example.diaryapp.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.diaryapp.presentation.ui.component.card.LoginCard
import com.example.diaryapp.presentation.ui.component.textField.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.logo.Logo
import com.example.diaryapp.presentation.ui.component.textField.PasswordTextField
import com.example.diaryapp.presentation.ui.component.button.TextOnlyButton
import com.example.diaryapp.presentation.ui.event.LoginEvent
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit = {},
    onFindScreen: () -> Unit = {},
    onSignupScreen: () -> Unit = {},
    showToast: (String) -> Unit

) {
    val autoLogin by viewModel.autoLogin.collectAsState()

    val uiState = viewModel.uiState
    val focusManager = LocalFocusManager.current

    LaunchedEffect(autoLogin) {
        if (autoLogin) {
            viewModel.tryAutoLogin()
        }
    }

    // 로그인 성공/실패 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> onLoginSuccess(event.userName)
                is LoginEvent.LoginFailed -> {
                    showToast(event.message)
                }
            }
        }
    }

    Scaffold {
        Box(
            Modifier
                .fillMaxSize()
                .background(BackGround)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 180.dp, end = 10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Logo()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LoginCard(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    IdTextField(
                        value = uiState.userName,
                        onValueChange = viewModel::onIdChange,
                        label = "아이디",
                        isError = uiState.idError != null
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth()
                    ) {
                        if (uiState.idError != null) {
                            Text(
                                text = "• ${uiState.idError}",
                                color = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        } else {
                            Text(
                                text = "• 아이디는 8~16자, 특수문자 제외",
                                color = PrimaryAccent,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    PasswordTextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPwChange,
                        label = "비밀번호",
                        isError = uiState.pwError != null
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 1.dp)
                            .fillMaxWidth()
                    ) {
                        if (uiState.pwError != null) {
                            Text(
                                text = "• ${uiState.pwError}",
                                color = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        } else {
                            Text(
                                text = "• 비밀번호는 8~16자, 숫자/영문/특수문자 포함",
                                color = PrimaryAccent,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(start = 200.dp, bottom = 10.dp)
                    ) {
                        Checkbox(
                            checked = autoLogin,
                            onCheckedChange = viewModel::onAutoLoginChange,
                            colors = CheckboxDefaults.colors(
                                checkedColor = PrimaryAccent,
                                uncheckedColor = PrimaryAccent,
                                checkmarkColor = BackGround,
                                disabledUncheckedColor = PrimaryAccent
                            )
                        )
                        Text("자동 로그인")
                    }
                    LoginButton(
                        text = "로그인",
                        onClick = {
                            viewModel.login()
                        },
                        enabled = !uiState.isLoading
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        TextOnlyButton(
                            text = "비밀번호 찾기",
                            onClick = onFindScreen
                        )
                        TextOnlyButton(
                            text = "회원가입",
                            onClick = onSignupScreen
                        )
                    }
                }
            }
        }
    }
}
