package com.example.diaryapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT name FROM folders WHERE userName = :userName")
    fun getFolders(userName: String): Flow<List<String>>

    // 특정 폴더 이름으로 변경 (전체 일기)
    @Query("UPDATE diary SET folder = :newName WHERE userName = :userName AND folder = :oldName")
    suspend fun renameFolder(userName: String, oldName: String, newName: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFolder(folder: FolderEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM folders WHERE userName = :userName AND name = :folderName LIMIT 1)") // New method
    suspend fun folderExists(userName: String, folderName: String): Boolean

    @Query("""
    SELECT * FROM diary
    WHERE userName = :userName
      AND folder = :folder
      AND (title LIKE '%' || :keyword || '%' 
           OR content LIKE '%' || :keyword || '%')""")
    fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryEntity>>
}