package com.example.diaryapp.presentation.ui.screen.findpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.card.LoginCard
import com.example.diaryapp.presentation.ui.component.logo.Logo
import com.example.diaryapp.presentation.ui.component.input.PasswordTextField
import com.example.diaryapp.presentation.ui.component.button.BackButton
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.DiaryAppTheme
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.Jua
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.viewmodel.ResetPasswordUiState

@Composable
fun ResetPasswordScreen(
    newPassword: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChane: (String) -> Unit,
    onClick: () -> Unit,
    onBackClick: () -> Unit,
    uiState: ResetPasswordUiState
) {

    val focusManager = LocalFocusManager.current

    DiaryAppTheme {
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
            BackButton(
                onBackClick
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 145.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.TopCenter
            ) {
                Logo()
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 210.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "비밀번호 찾기",
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
                    PasswordTextField(
                        value = newPassword,
                        onValueChange = { onPasswordChange(it) },
                        label = "새 비밀번호",
                        isError = uiState.pwError != null,
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        if(uiState.pwError != null){
                            Text(
                                text = "• ${uiState.pwError}",
                                color = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        } else {
                            Text(
                                text = "• 비밀번호는 8~16자, 숫자/영문/특수문자 포함",
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    PasswordTextField(
                        value = confirmPassword,
                        onValueChange = { onConfirmPasswordChane(it) },
                        label = "새 비밀번호 확인",
                        isError = uiState.confirmPwError != null,
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        if(uiState.confirmPwError != null){
                            Text(
                                text = "• ${uiState.confirmPwError}",
                                color = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        } else {
                            Text(
                                text = "• 비밀번호를 한번 더 입력해주세요",
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    LoginButton(
                        text = "비밀번호 변경",
                        onClick = onClick,
                        enabled = !uiState.isLoading
                    )
                }
            }
        }
    }
}