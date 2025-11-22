package com.example.diaryapp.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val folder: String?,
    val title: String,
    val content: String,
    val createDate: Date,
    val updateDate: Date
)