package com.example.diaryapp.domain.usecase.diary

import com.example.diaryapp.domain.model.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryUseCase {
    suspend fun writeDiary(diary: Diary)
    suspend fun updateDiary(diary: Diary)
    suspend fun deleteDiary(id: Int)
    fun getDiaryById(id: Int): Flow<Diary?>
    fun getDiariesByUser(userName: String): Flow<List<Diary>>
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<Diary>>
    fun getFavoriteDiaries(userName: String): Flow<List<Diary>>
    suspend fun toggleFavoriteStatus(id: Int, isFavorite: Boolean)
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<Diary>>
    fun searchTimeline(userName: String, keyword: String): Flow<List<Diary>>
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<Diary>>
    fun getFolders(userName: String): Flow<List<String>>
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
    suspend fun addFolder(userName: String, folderName: String)
}
