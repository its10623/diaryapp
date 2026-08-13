package com.example.diaryapp.domain.repository

import com.example.diaryapp.domain.model.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getDiaryById(id: Int): Flow<Diary?>
    suspend fun insertDiary(diary: Diary)
    suspend fun updateDiary(diary: Diary)
    suspend fun deleteDiary(id: Int)
    fun getDiariesByUser(userName: String): Flow<List<Diary>>
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<Diary>>
    fun getFavoriteDiaries(userName: String): Flow<List<Diary>>
    suspend fun toggleFavoriteStatus(id: Int, isFavorite: Boolean)
    fun getFolders(userName: String): Flow<List<String>>
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<Diary>>
    fun searchTimeline(userName: String, keyword: String): Flow<List<Diary>>
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<Diary>>
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
    suspend fun addFolder(userName: String, folderName: String)
    suspend fun folderExists(userName: String, folderName: String): Boolean
}
