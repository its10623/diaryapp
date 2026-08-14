package com.example.diaryapp.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun PasswordStep(
    stepState: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "① 계정 확인",
            style = TextStyle(
                fontWeight = if (stepState == 1) FontWeight.Bold else FontWeight.Normal,
                fontFamily = SansFamily,
                fontSize = if (stepState == 1) 14.sp else 12.sp,
                color = if (stepState == 1) PrimaryAccent else TextSub2,
                letterSpacing = 0.5.sp
            )
        )
        HorizontalDivider(
            color = Border1,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
        )
        Text(
            text = "② 재설정",
            style = TextStyle(
                fontWeight = if (stepState == 2) FontWeight.Bold else FontWeight.Normal,
                fontFamily = SansFamily,
                fontSize = if (stepState == 2) 14.sp else 12.sp,
                color = if (stepState == 2) PrimaryAccent else TextSub2,
                letterSpacing = 0.5.sp
            )
        )
    }
}