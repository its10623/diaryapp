package com.example.diaryapp.presentation.ui.component.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun TextOnlyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Transparent,
            contentColor = PrimaryAccent,

            )
    ) {
        Text(
            text = text
        )
    }
}