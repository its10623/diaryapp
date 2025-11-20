package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diaryapp.presentation.ui.component.card.LoginCard
import com.example.diaryapp.presentation.ui.component.input.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.logo.Logo
import com.example.diaryapp.presentation.ui.component.input.PasswordTextField
import com.example.diaryapp.presentation.ui.component.button.TextOnlyButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.DiaryAppTheme // Import your custom theme
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onFindScreen: () -> Unit = {},
    onSignupScreen: () -> Unit = {},
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold() {
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
                        value = id,
                        onValueChange = { id = it },
                        label = "아이디"
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "• 아이디는 8~16자, 특수문자 제외",
                            color = PrimaryAccent,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "비밀번호"
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 1.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "• 비밀번호는 8~16자, 숫자/영문/특수문자 포함",
                            color = PrimaryAccent,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                    LoginButton(
                        text = "로그인",
                        onClick = onLoginSuccess,
                            // TODO 추후 DB연동하여 작동체크
                        enabled = id.isNotBlank()
                                && password.isNotBlank()
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
