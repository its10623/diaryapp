package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    Box(
    modifier = Modifier
    .background(color = BackGround)
    .fillMaxSize(),
    contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.notebook_6343437),
                contentDescription = "앱 로고",
                modifier = Modifier
                    .size(116.dp)
                    .padding(end = 1.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "모노",
                    textAlign = TextAlign.Start,
                    style = LogoTextStyle.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 55.sp
                    ),
                    color = PrimaryAccent
                )
                Text(
                    text = "다이어리",
                    textAlign = TextAlign.Start,
                    style = LogoTextStyle.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 55.sp
                    ),
                    color = PrimaryAccent
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }
}