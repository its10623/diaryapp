package com.example.diaryapp.data.local.room

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM diary WHERE userName = :userName ORDER BY createDate DESC")
    fun getDiariesByUser(userName: String): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary WHERE userName = :userName AND folder = :folder ORDER BY createDate DESC")
    fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryEntity>>

    @Query("SELECT DISTINCT folder FROM diary WHERE userName = :userName AND folder IS NOT NULL")
    fun getFolders(userName: String): Flow<List<String>>

    @Query("SELECT * FROM diary WHERE id = :id LIMIT 1")
    fun getDiaryById(id: Int): Flow<DiaryEntity?>

    @Query("""
    SELECT * FROM diary
    WHERE userName = :userName
      AND folder = :folder
      AND (title LIKE '%' || :keyword || '%' 
           OR content LIKE '%' || :keyword || '%')
    ORDER BY createDate DESC
""")
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryEntity>>

    @Query("""
    SELECT * FROM diary
    WHERE userName = :userName
      AND (title LIKE '%' || :keyword || '%' 
           OR content LIKE '%' || :keyword || '%')
    ORDER BY createDate DESC
""")
    fun searchTimeline(
        userName: String,
        keyword: String
    ): Flow<List<DiaryEntity>>

    @Query("""SELECT * FROM diary WHERE userName = :userName AND createDate BETWEEN :start AND :end ORDER BY createDate DESC""")
    fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryEntity>>

    // 특정 폴더 이름으로 변경 (전체 일기)
    @Query("UPDATE diary SET folder = :newName WHERE userName = :userName AND folder = :oldName")
    suspend fun renameFolder(userName: String, oldName: String, newName: String)
}
