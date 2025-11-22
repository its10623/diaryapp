package com.example.diaryapp.presentation.ui.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BoundaryLine
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.TextHintStyle
import com.example.diaryapp.presentation.ui.theme.inter

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        // 엔터누르면 키보드 다운
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        // 엔터를 Done으로 교체
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        label = {
            Text(
                text = label,
                style = TextHintStyle
            )
        },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,           // 한 줄 입력 처리용
        textStyle = TextStyle(
            fontFamily = inter,
            color = PrimaryAccent,
            fontSize = 16.sp
        ),
        visualTransformation = PasswordVisualTransformation(),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = PrimaryAccent,
            unfocusedIndicatorColor = BoundaryLine,

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            errorCursorColor = ErrorColor,
        )
    )
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = ErrorColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}