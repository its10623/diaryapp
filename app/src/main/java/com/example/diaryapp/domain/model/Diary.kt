package com.example.diaryapp.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Diary(
    val id: Long = System.currentTimeMillis(),      // 현재 시간 기준으로 고유한 ID를 자동 생성
    val name: String,
    val date: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    val title: String,
    val content: String
)