package com.example.diaryapp.dto

import com.example.diaryapp.domain.model.Diary

data class DiaryDto(
    val id: Long? = null,
    val name: String,
    val date: String? = null,
    val title: String,
    val content: String
)

fun Diary.toDiaryDto(): DiaryDto {
    return DiaryDto(
        this.id,
        this.name,
        this.date,
        this.title,
        this.content
    )
}

fun DiaryDto.toDiary(): Diary {
    return Diary(
        name = this.name,
        title = this.title,
        content = this.content
    )
}