package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.inter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateHeader(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Text(
        text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd (E)")),
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 16.dp),
        fontFamily = inter,
        fontWeight = Bold,
        fontSize = 20.sp,
        color = PrimaryAccent
    )
}