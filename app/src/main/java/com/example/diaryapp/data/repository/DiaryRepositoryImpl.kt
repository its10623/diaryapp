package com.example.diaryapp.data.repository

import com.example.diaryapp.data.local.room.DiaryDao
import com.example.diaryapp.data.mapper.toDto
import com.example.diaryapp.data.mapper.toEntity
import com.example.diaryapp.domain.repository.DiaryRepository
import com.example.diaryapp.dto.DiaryDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryRepository {

    override fun getDiaryById(id: Int): Flow<DiaryDto?> =
        diaryDao.getDiaryById(id).map { entity ->
            entity?.toDto()
        }

    override suspend fun insertDiary(diary: DiaryDto) {
        diaryDao.insertDiary(diary.toEntity())
    }

    override suspend fun updateDiary(diary: DiaryDto) {
        diaryDao.updateDiary(diary.toEntity())
    }

    override suspend fun deleteDiary(id: Int) {
        diaryDao.deleteDiary(id)
    }

    override fun getDiariesByUser(userName: String): Flow<List<DiaryDto>> =
        diaryDao.getDiariesByUser(userName).map { entities ->
            entities.map { it.toDto() }
        }

    override fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryDto>> =
        diaryDao.getDiariesByFolder(userName, folder).map { entities ->
            entities.map { it.toDto() }
        }

    override fun getFolders(userName: String): Flow<List<String>> =
        diaryDao.getFolders(userName)

    override fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryDto>> =
        diaryDao.searchInFolder(userName, folder, keyword).map { entities ->
            entities.map { it.toDto() }
        }

    override fun searchTimeline(userName: String, keyword: String): Flow<List<DiaryDto>> =
        diaryDao.searchTimeline(userName, keyword).map { entities ->
            entities.map { it.toDto() }
        }

    override fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryDto>> =
        diaryDao.filterByDate(userName, start, end).map { entities ->
            entities.map { it.toDto() }
        }

    override suspend fun renameFolder(userName: String, oldName: String, newName: String) {
        diaryDao.renameFolder(userName, oldName, newName)
    }
}
