package com.example.diaryapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert
    suspend fun insertDiary(entity: DiaryEntity): Long

    @Update
    suspend fun updateDiary(entity: DiaryEntity)

    @Query("DELETE FROM diary WHERE id = :id")
    suspend fun deleteDiary(id: Int)

    @Query("SELECT * FROM diary WHERE userName = :userName")
    fun getDiariesByUser(userName: String): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary WHERE userName = :userName AND folder = :folder")
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary WHERE userName = :userName AND isFavorite = 1")
    fun getFavoriteDiaries(userName: String): Flow<List<DiaryEntity>>

    @Query("UPDATE diary SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT DISTINCT folder FROM diary WHERE userName = :userName AND folder IS NOT NULL")
    fun getFolders(userName: String): Flow<List<String>>

    @Query("SELECT * FROM diary WHERE id = :id LIMIT 1")
    fun getDiaryById(id: Int): Flow<DiaryEntity?>


    @Query(
        """
    SELECT * FROM diary
    WHERE userName = :userName
      AND (title LIKE '%' || :keyword || '%' 
           OR content LIKE '%' || :keyword || '%')"""
    )
    fun searchTimeline(
        userName: String,
        keyword: String
    ): Flow<List<DiaryEntity>>

    @Query("""SELECT * FROM diary WHERE userName = :userName AND createDate BETWEEN :start AND :end""")
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryEntity>>

    // 특정 폴더 이름으로 변경 (전체 일기)
    @Query("UPDATE diary SET folder = :newName WHERE userName = :userName AND folder = :oldName")
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
}
