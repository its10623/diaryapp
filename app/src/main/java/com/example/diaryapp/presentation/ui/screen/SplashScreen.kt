package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.LabelTrackingStyle
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround)
            .clickable { onSplashFinished() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "모노 다이어리",
                style = LogoTextStyle.copy(fontSize = 60.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "하루를 기록하는 가장 단순한 방법",
                style = TextStyle(
                    fontFamily = SansFamily,
                    fontSize = 13.sp,
                    color = TextSub2,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        Text(
            text = "TAP TO CONTINUE",
            style = LabelTrackingStyle.copy(letterSpacing = 3.sp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 52.dp)
        )
    }
}
