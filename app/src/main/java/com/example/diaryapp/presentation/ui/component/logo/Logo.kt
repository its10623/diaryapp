package com.example.diaryapp.presentation.ui.component.logo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Text(
        text = "모노 다이어리",
        style = LogoTextStyle,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
