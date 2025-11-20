package com.example.diaryapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R

val Hanna = FontFamily(Font(R.font.bm_hanna_pro))
val Jua = FontFamily(Font(R.font.bm_jua_content))
val Dohyeon = FontFamily(Font(R.font.bm_dohyeon))
val inter = FontFamily(Font(R.font.inter_variable))
val Typography = Typography(
    bodyLarge = TextStyle(              // 아웃라인 텍스트 필드, 텍스트
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    titleLarge = TextStyle(             // 탑 앱바
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelLarge = TextStyle(             // 버튼
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(            // 텍스트 필드
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
)
val TextHintStyle = TextStyle(
    fontFamily = inter,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = TextHint
)
val LogoTextStyle = TextStyle(
    fontFamily = Jua,
    fontWeight = FontWeight.Normal,
    fontSize = 50.sp,
    color = PrimaryAccent
)
val DiaryTitleStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontFamily = Jua,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None // 아래 잘림 방지
    ),
    color = PrimaryAccent
)
val DiaryContentStyle = TextStyle(
    fontFamily = Jua,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None // 아래 잘림 방지
    ),
    color = PrimaryAccent
)