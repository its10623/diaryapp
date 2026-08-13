package com.example.diaryapp.presentation.ui.screen.findpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.textField.PasswordTextField
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.DiaryAppTheme
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2
import com.example.diaryapp.presentation.ui.uiState.ResetPasswordUiState

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { focusManager.clearFocus() }
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    style = TextStyle(fontFamily = SansFamily, fontSize = 20.sp, color = PrimaryAccent),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onBackClick() }
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "비밀번호 재설정",
                    style = TextStyle(fontFamily = SansFamily, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = PrimaryAccent)
                )
                Spacer(Modifier.weight(1f))
            }

            HorizontalDivider(color = Border1)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
            ) {
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "① 계정 확인  ② 재설정",
                    style = TextStyle(fontFamily = SansFamily, fontSize = 12.sp, color = TextSub2, letterSpacing = 0.5.sp)
                )

                Spacer(Modifier.height(32.dp))

                PasswordTextField(
                    value = newPassword,
                    onValueChange = { onPasswordChange(it) },
                    label = "새 비밀번호",
                    isError = uiState.pwError != null,
                    errorMessage = uiState.pwError
                )

                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = { onConfirmPasswordChane(it) },
                    label = "새 비밀번호 확인",
                    isError = uiState.confirmPwError != null,
                    errorMessage = uiState.confirmPwError
                )

                Spacer(Modifier.height(32.dp))

                LoginButton(
                    text = "비밀번호 변경",
                    onClick = onClick,
                    enabled = !uiState.isLoading
                )
            }
        }
    }
}
