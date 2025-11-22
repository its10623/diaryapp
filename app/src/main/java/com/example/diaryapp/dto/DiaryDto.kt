package com.example.diaryapp.dto

import java.util.Date

data class DiaryDto(
    val id: Int = 0,
    val userName: String,
    val folder: String?,
    val title: String,
    val content: String,
    val createDate: Date,
    val updateDate: Date
)
