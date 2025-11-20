package com.example.diaryapp.presentation.ui.screen

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.navigation.BottomNavBar
import com.example.diaryapp.presentation.ui.navigation.Screen
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun CalenderScreen(
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "업데이트 예정",
                color = Color.Gray,
                fontSize = 50.sp,
                fontWeight = Bold,
            )
        }
    }
}