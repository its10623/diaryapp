package com.example.diaryapp.presentation.ui.screen.findpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun FindPasswordFlowScreen(
    onFindSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var step by remember { mutableStateOf(1) }

    when(step) {

        1 -> {
            FindPasswordScreen(
                id = id,
                onValueChange = { id = it },
                onClick = { step = 2 },
                onBackClick = onNavigateBack
            )
        }

        2 -> {
            ChangePasswordScreen(
                newPassword = newPassword,
                onPasswordChange = { newPassword = it },
                confirmPassword = confirmNewPassword,
                onConfirmPasswordChane = { confirmNewPassword = it},
                onClick = onFindSuccess,
                onBackClick = { step = 1 }
            )
        }
    }
}