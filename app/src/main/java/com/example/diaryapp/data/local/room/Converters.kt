package com.example.diaryapp.data.local.room

import androidx.room.TypeConverter
import java.util.Date

// Room은 Date타입을 저장 할 수 없어서 타입컨버터로 변환이 필요
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
