package com.example.diaryapp.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.solver.widgets.Optimizer.enabled
import androidx.navigation.NavController
import com.example.diaryapp.presentation.ui.component.input.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.card.LoginCard
import com.example.diaryapp.presentation.ui.component.logo.Logo
import com.example.diaryapp.presentation.ui.component.input.PasswordTextField
import com.example.diaryapp.presentation.ui.component.button.BackButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.DiaryAppTheme
import com.example.diaryapp.presentation.ui.theme.Jua
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    onSignupSuccess: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                        value = id,
                        onValueChange = { id = it },
                        label = "아이디"
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
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
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "• 비밀번호는 8~16자, 숫자/영문/특수문자 포함",
                            color = PrimaryAccent,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                    PasswordTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "비밀번호 확인"
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "• 비밀번호를 한번 더 입력해주세요",
                            color = PrimaryAccent,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }

                    LoginButton(
                        text = "회원가입",
                        onClick = { onSignupSuccess() },
                        enabled = id.isNotBlank()
                                && password.isNotBlank()
                                && confirmPassword.isNotBlank()
                    )
                }
            }
        }
    }
}
