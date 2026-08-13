package com.example.diaryapp.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryAccent,
    onPrimary = ButtonText,
    background = BackGround,
    onBackground = PrimaryAccent,
    surface = BackGround,
    onSurface = PrimaryAccent,
    surfaceVariant = InputBackground,
    onSurfaceVariant = TextSub1,
    outline = Border1,
    outlineVariant = Border2,
    error = ErrorColor,
    onError = Color.White,
    primaryContainer = PrimaryAccent,
    onPrimaryContainer = ButtonText,
)

@Composable
fun DiaryAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
