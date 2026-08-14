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
import com.example.diaryapp.presentation.ui.component.textField.IdTextField
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.uiState.FindPasswordUiState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import com.example.diaryapp.presentation.ui.component.PasswordStep

@Composable
fun FindPasswordScreen(
    id: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    onBackClick: () -> Unit,
    uiState: FindPasswordUiState,
    stepState: Int
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
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
                        text = "비밀번호 찾기",
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 28.dp)
                ) {
                    Spacer(Modifier.height(20.dp))

                    // Step indicator
                    PasswordStep(stepState)

                    Spacer(Modifier.height(32.dp))

                    IdTextField(
                        value = id,
                        onValueChange = { onValueChange(it) },
                        label = "아이디",
                        isError = uiState.idError != null,
                        errorMessage = uiState.idError
                    )

                    Spacer(Modifier.height(32.dp))

                    LoginButton(
                        text = "다음",
                        onClick = onClick,
                        enabled = !uiState.isLoading
                    )
                }
            }
        }
    }
}
