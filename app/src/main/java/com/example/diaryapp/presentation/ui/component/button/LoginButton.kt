package com.example.diaryapp.presentation.ui.component.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.ButtonText
import com.example.diaryapp.presentation.ui.theme.DisableButton
import com.example.diaryapp.presentation.ui.theme.DisableButtonText
import com.example.diaryapp.presentation.ui.theme.PressedDarkButton
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor = if (isPressed) PressedDarkButton else PrimaryAccent

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 1.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = ButtonText,
            disabledContainerColor = DisableButton,
            disabledContentColor = DisableButtonText,
        )
    ) {
        Text(
            text = text,
            fontWeight = Black,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 1.dp)
        )
    }
}