package com.example.diaryapp.data.repository

import com.example.diaryapp.data.local.room.DiaryDao
import com.example.diaryapp.data.local.room.FolderDao
import com.example.diaryapp.data.local.room.FolderEntity
import com.example.diaryapp.data.mapper.toDomain
import com.example.diaryapp.data.mapper.toEntity
import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.domain.repository.DiaryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao,
    private val folderDao: FolderDao
) : DiaryRepository {

    override fun getDiaryById(id: Int): Flow<Diary?> =
        diaryDao.getDiaryById(id).map { it?.toDomain() }

    override suspend fun insertDiary(diary: Diary) {
        diaryDao.insertDiary(diary.toEntity())
    }

    override suspend fun updateDiary(diary: Diary) =
        diaryDao.updateDiary(diary.toEntity())

    override suspend fun deleteDiary(id: Int) =
        diaryDao.deleteDiary(id)

    override fun getDiariesByUser(userName: String): Flow<List<Diary>> =
        diaryDao.getDiariesByUser(userName).map { it.map { entity -> entity.toDomain() } }

    override fun getDiariesByFolder(userName: String, folder: String): Flow<List<Diary>> =
        diaryDao.getDiariesByFolder(userName, folder).map { it.map { entity -> entity.toDomain() } }

    override fun getFavoriteDiaries(userName: String): Flow<List<Diary>> =
        diaryDao.getFavoriteDiaries(userName).map { it.map { entity -> entity.toDomain() } }

    override suspend fun toggleFavoriteStatus(id: Int, isFavorite: Boolean) =
        diaryDao.updateFavoriteStatus(id, isFavorite)

    override fun getFolders(userName: String): Flow<List<String>> =
        folderDao.getFolders(userName)

    override fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<Diary>> =
        folderDao.searchInFolder(userName, folder, keyword).map { it.map { entity -> entity.toDomain() } }

    override fun searchTimeline(userName: String, keyword: String): Flow<List<Diary>> =
        diaryDao.searchTimeline(userName, keyword).map { it.map { entity -> entity.toDomain() } }

    override fun filterByDate(userName: String, start: Long, end: Long): Flow<List<Diary>> =
        diaryDao.filterByDate(userName, start, end).map { it.map { entity -> entity.toDomain() } }

    override suspend fun renameFolder(userName: String, oldName: String, newName: String) =
        folderDao.renameFolder(userName, oldName, newName)

    override suspend fun addFolder(userName: String, folderName: String) =
        folderDao.insertFolder(FolderEntity(userName = userName, name = folderName))

    override suspend fun folderExists(userName: String, folderName: String): Boolean =
        folderDao.folderExists(userName, folderName)
}
