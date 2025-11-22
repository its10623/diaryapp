package com.example.diaryapp.domain.repository

import com.example.diaryapp.dto.DiaryDto
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getDiaryById(id: Int): Flow<DiaryDto?>
    suspend fun insertDiary(diary: DiaryDto)
    suspend fun updateDiary(diary: DiaryDto)
    suspend fun deleteDiary(id: Int)
    fun getDiariesByUser(userName: String): Flow<List<DiaryDto>>
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryDto>>
    fun getFolders(userName: String): Flow<List<String>>
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryDto>>
    fun searchTimeline(userName: String, keyword: String): Flow<List<DiaryDto>>
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryDto>>
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
}
