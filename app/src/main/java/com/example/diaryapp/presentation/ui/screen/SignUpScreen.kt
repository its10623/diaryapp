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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diaryapp.presentation.ui.component.textField.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.component.textField.PasswordTextField
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
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
            Column(Modifier.fillMaxSize()) {
                // Header row
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
                        ) { onNavigateBack() }
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "회원가입",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = PrimaryAccent
                        )
                    )
                    Spacer(Modifier.weight(1f))
                }
                HorizontalDivider(color = Border1)

                // Fields
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 28.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(Modifier.height(24.dp))

                    IdTextField(
                        value = uiState.id,
                        onValueChange = { viewModel.onIdChange(it) },
                        label = "아이디",
                        isError = uiState.idError != null,
                        errorMessage = uiState.idError
                    )

                    PasswordTextField(
                        value = uiState.pw,
                        onValueChange = { viewModel.onPwChange(it) },
                        label = "비밀번호",
                        isError = uiState.pwError != null,
                        errorMessage = uiState.pwError
                    )

                    PasswordTextField(
                        value = uiState.confirmPw,
                        onValueChange = { viewModel.onConfirmPwChange(it) },
                        label = "비밀번호 확인",
                        isError = uiState.confirmPwError != null,
                        errorMessage = uiState.confirmPwError
                    )

                    Spacer(Modifier.height(32.dp))

                    LoginButton(
                        text = "회원가입",
                        onClick = { viewModel.register() },
                        enabled = !uiState.isLoading
                    )

                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
}
