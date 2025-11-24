package com.example.diaryapp.application.usecase

import com.example.diaryapp.dto.DiaryDto
import kotlinx.coroutines.flow.Flow

interface DiaryUseCase {
    suspend fun writeDiary(diary: DiaryDto)
    suspend fun updateDiary(diary: DiaryDto)
    suspend fun deleteDiary(id: Int)
    fun getDiaryById(id: Int): Flow<DiaryDto?>
    fun getDiariesByUser(userName: String): Flow<List<DiaryDto>>
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryDto>>
    fun getFavoriteDiaries(userName: String): Flow<List<DiaryDto>> // New method
    suspend fun toggleFavoriteStatus(id: Int, isFavorite: Boolean) // New method
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryDto>>
    fun searchTimeline(userName: String, keyword: String): Flow<List<DiaryDto>>
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryDto>>
    fun getFolders(userName: String): Flow<List<String>>
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
    suspend fun addFolder(userName: String, folderName: String)
}
