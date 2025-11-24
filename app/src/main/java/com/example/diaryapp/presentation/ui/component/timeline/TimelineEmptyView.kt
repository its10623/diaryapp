package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.TextHintStyle

@Composable
fun TimelineEmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "아직 작성된 일기가 없어요.",
            style = LogoTextStyle.copy(fontSize = 22.sp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "오늘의 기록을 남겨보세요!",
            style = TextHintStyle.copy(color = PrimaryAccent.copy(alpha = 0.7f))
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}