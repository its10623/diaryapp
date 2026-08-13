package com.example.diaryapp.data.mapper

import com.example.diaryapp.data.local.room.DiaryEntity
import com.example.diaryapp.domain.model.Diary

fun DiaryEntity.toDomain() = Diary(
    id = id,
    userName = userName,
    folder = folder,
    title = title,
    content = content,
    createDate = createDate,
    updateDate = updateDate,
    isFavorite = isFavorite
)

fun Diary.toEntity() = DiaryEntity(
    id = id,
    userName = userName,
    folder = folder,
    title = title,
    content = content,
    createDate = createDate,
    updateDate = updateDate,
    isFavorite = isFavorite
)
