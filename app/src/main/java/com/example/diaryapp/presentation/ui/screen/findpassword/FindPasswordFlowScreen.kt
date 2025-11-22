package com.example.diaryapp.presentation.ui.screen.findpassword

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.diaryapp.presentation.viewmodel.FindPasswordEvent
import com.example.diaryapp.presentation.viewmodel.FindPasswordViewModel
import com.example.diaryapp.presentation.viewmodel.ResetPasswordUiEvent
import com.example.diaryapp.presentation.viewmodel.ResetPasswordViewModel

@Composable
fun FindPasswordFlowScreen(
    onFindSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val findViewModel: FindPasswordViewModel = hiltViewModel()
    val resetViewModel: ResetPasswordViewModel = hiltViewModel()

    val findState = findViewModel.uiState
    val resetState = resetViewModel.uiState

    var step by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        findViewModel.event.collect { event ->
            when (event) {
                is FindPasswordEvent.Success ->  {
                    resetViewModel.initUserId(event.id)
                    step = 2
                }
                is FindPasswordEvent.Fail -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        resetViewModel.event.collect { event ->
            when (event) {
                is ResetPasswordUiEvent.Success -> onFindSuccess()
                is ResetPasswordUiEvent.Fail ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    when(step) {
        1 -> {
            FindPasswordScreen(
                id = findState.id,
                onValueChange = findViewModel::onIdChange,
                onClick = findViewModel::checkUserId,
                onBackClick = onNavigateBack,
                uiState = findState
            )
        }

        2 -> {
            ResetPasswordScreen(
                newPassword = resetState.newPw,
                onPasswordChange = resetViewModel::onPwChange,
                confirmPassword = resetState.confirmPw,
                onConfirmPasswordChane = resetViewModel::onConfirmPwChange,
                onClick = resetViewModel::resetPassword,
                onBackClick = { step = 1 },
                uiState = resetState
            )
        }
    }
}