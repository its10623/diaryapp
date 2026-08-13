package com.example.diaryapp.domain.model

import java.util.Date

data class Diary(
    val id: Int = 0,
    val userName: String,
    val folder: String?,
    val title: String,
    val content: String,
    val createDate: Date,
    val updateDate: Date,
    val isFavorite: Boolean = false
)
