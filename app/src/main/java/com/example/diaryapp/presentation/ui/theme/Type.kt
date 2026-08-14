package com.example.diaryapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val notoSerifKr = GoogleFont("Noto Serif KR")
private val notoSansKr = GoogleFont("Noto Sans KR")

val SerifFamily = FontFamily(
    Font(googleFont = notoSerifKr, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = notoSerifKr, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = notoSerifKr, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = notoSerifKr, fontProvider = provider, weight = FontWeight.Bold),
)

val SansFamily = FontFamily(
    Font(googleFont = notoSansKr, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = notoSansKr, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = notoSansKr, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = notoSansKr, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = notoSansKr, fontProvider = provider, weight = FontWeight.ExtraBold),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = SansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = SansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = SansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = SansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.04.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = SansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 0.08.sp,
    ),
)
val LogoTextStyle = TextStyle(
    fontFamily = SerifFamily,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Normal,
    fontSize = 46.sp,
    color = PrimaryAccent,
)

val DiaryTitleStyle = TextStyle(
    fontFamily = SerifFamily,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
    color = PrimaryAccent,
)

val DiaryContentStyle = TextStyle(
    fontFamily = SerifFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 26.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
    color = PrimaryAccent,
)

val TextHintStyle = TextStyle(
    fontFamily = SansFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = TextSub2,
)

val LabelTrackingStyle = TextStyle(
    fontFamily = SansFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    letterSpacing = 1.5.sp,
    color = TextSub2,
)
