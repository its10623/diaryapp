package com.example.diaryapp.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diaryapp.presentation.ui.component.textField.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.card.LoginCard
import com.example.diaryapp.presentation.ui.component.logo.Logo
import com.example.diaryapp.presentation.ui.component.textField.PasswordTextField
import com.example.diaryapp.presentation.ui.component.button.BackButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.Jua
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.event.RegisterEvent
import com.example.diaryapp.presentation.viewmodel.RegisterViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onSignupSuccess: () -> Unit = {},
    showToast: (String) -> Unit,
    onNavigateBack: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is RegisterEvent.Success -> onSignupSuccess()
                is RegisterEvent.Fail -> showToast(event.message)
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
            BackButton(onNavigateBack)
            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.TopCenter
            ) {
                Logo()
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 185.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "회원가입",
                    fontSize = 35.sp,
                    fontWeight = ExtraBold,
                    fontFamily = Jua,
                    color = PrimaryAccent

                )
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
                        value = uiState.id,
                        onValueChange = { viewModel.onIdChange(it) },
                        label = "아이디",
                        isError = uiState.idError != null,
                    )

                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
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
                                text = "• 아이디는 4~20자, 특수문자 제외",
                                color = PrimaryAccent,
                                modifier = Modifier.align(Alignment.BottomStart)

                            )
                        }
                    }
                    PasswordTextField(
                        value = uiState.pw,
                        onValueChange = { viewModel.onPwChange(it) },
                        label = "비밀번호",
                        isError = uiState.pwError != null || uiState.confirmPwError != null
                    )

                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
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
                    PasswordTextField(
                        value = uiState.confirmPw,
                        onValueChange = { viewModel.onConfirmPwChange(it) },
                        label = "비밀번호 확인",
                        isError = uiState.confirmPwError != null,
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        if (uiState.confirmPwError != null) {
                            Text(
                                text = "• ${uiState.confirmPwError}",
                                color = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        } else {
                            Text(
                                text = "• 비밀번호를 한번 더 입력해주세요",
                                color = PrimaryAccent,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }

                    LoginButton(
                        text = "회원가입",
                        onClick = { viewModel.register() },
                        enabled = !uiState.isLoading
                    )
                }
            }
        }
    }
}
