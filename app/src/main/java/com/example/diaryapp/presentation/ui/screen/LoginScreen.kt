package com.example.diaryapp.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.GoogleAccountUtil
import com.example.diaryapp.presentation.ui.component.textField.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.textField.PasswordTextField
import com.example.diaryapp.presentation.ui.event.LoginEvent
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2
import com.example.diaryapp.presentation.ui.theme.TextSub3
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

    // 로그인 성공/실패 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> onLoginSuccess(event.currentUserKey)
                is LoginEvent.LoginFailed -> {
                    showToast(event.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.tryAutoLogin()
    }

    if (uiState.isCheckingAutoLogin) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PrimaryAccent)
        }
        return
    }

    Scaffold { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .background(BackGround)
                .padding(paddingValues)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(Modifier.height(60.dp))

                // Logo text (serif italic)
                Text(
                    text = "모노 다이어리",
                    style = LogoTextStyle,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "다시 만나서 반가워요",
                    style = TextStyle(fontFamily = SansFamily, fontSize = 14.sp, color = TextSub2)
                )

                Spacer(Modifier.height(48.dp))

                // ID field + error
                IdTextField(
                    value = uiState.userName,
                    onValueChange = viewModel::onIdChange,
                    label = "아이디",
                    isError = uiState.idError != null,
                    errorMessage = uiState.idError
                )

                // PW field + error
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPwChange,
                    label = "비밀번호",
                    isError = uiState.pwError != null,
                    errorMessage = uiState.pwError
                )

                Spacer(Modifier.height(16.dp))

                // Auto-login checkbox (right-aligned)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = autoLogin,
                        onCheckedChange = viewModel::onAutoLoginChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = PrimaryAccent,
                            uncheckedColor = TextSub2,
                            checkmarkColor = BackGround
                        )
                    )
                    Text(
                        "자동 로그인",
                        style = TextStyle(fontFamily = SansFamily, fontSize = 13.sp, color = TextSub2)
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Login button
                LoginButton(
                    text = "로그인",
                    onClick = { viewModel.login() },
                    enabled = !uiState.isLoading
                )

                Spacer(Modifier.height(16.dp))

                // Google sign-in
                GoogleAccountUtil(
                    onSuccess = { email, idToken -> viewModel.loginWithGoogle(email, idToken) },
                    onError = { message -> showToast(message) }
                )

                Spacer(Modifier.height(28.dp))

                // Text links: 비밀번호 찾기 | 회원가입
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "비밀번호 찾기",
                        style = TextStyle(fontFamily = SansFamily, fontSize = 13.sp, color = TextSub2),
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onFindScreen() }
                    )
                    Text(
                        text = "  |  ",
                        style = TextStyle(fontFamily = SansFamily, fontSize = 13.sp, color = TextSub3)
                    )
                    Text(
                        text = "회원가입",
                        style = TextStyle(fontFamily = SansFamily, fontSize = 13.sp, color = TextSub2),
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onSignupScreen() }
                    )
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
