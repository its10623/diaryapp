package com.example.diaryapp.application.usecase

import com.example.diaryapp.application.validator.DiaryValidator
import com.example.diaryapp.application.validator.FolderValidator
import com.example.diaryapp.domain.repository.DiaryRepository
import com.example.diaryapp.dto.DiaryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val diaryValidator: DiaryValidator,
    private val folderValidator: FolderValidator
) : DiaryUseCase {

    override suspend fun writeDiary(diary: DiaryDto) {
        diaryValidator.validateTitleInput(diary.title)
        diaryValidator.validateContentInput(diary.content)
        diaryRepository.insertDiary(diary)
    }

    override suspend fun updateDiary(diary: DiaryDto) {
        diaryValidator.validateTitleInput(diary.title)
        diaryValidator.validateContentInput(diary.content)
        diaryRepository.updateDiary(diary)
    }

    override suspend fun deleteDiary(id: Int) {
        diaryRepository.deleteDiary(id)
    }

    override fun getDiaryById(id: Int): Flow<DiaryDto?> =
        diaryRepository.getDiaryById(id)

    override fun getDiariesByUser(userName: String): Flow<List<DiaryDto>> =
        diaryRepository.getDiariesByUser(userName)

    override fun getDiariesByFolder(userName: String, folder: String): Flow<List<DiaryDto>> =
        diaryRepository.getDiariesByFolder(userName, folder)

    override fun getFavoriteDiaries(userName: String): Flow<List<DiaryDto>> =
        diaryRepository.getFavoriteDiaries(userName)

    override suspend fun toggleFavoriteStatus(id: Int, isFavorite: Boolean) {
        diaryRepository.toggleFavoriteStatus(id, isFavorite)
    }

    override fun searchInFolder(
        userName: String,
        folder: String,
        keyword: String
    ): Flow<List<DiaryDto>> =
        diaryRepository.searchInFolder(userName, folder, keyword)

    override fun searchTimeline(userName: String, keyword: String): Flow<List<DiaryDto>> =
        diaryRepository.searchTimeline(userName, keyword)

    override fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryDto>> =
        diaryRepository.filterByDate(userName, start, end)

    override fun getFolders(userName: String): Flow<List<String>> =
        diaryRepository.getFolders(userName)

    override suspend fun renameFolder(userName: String, oldName: String, newName: String) {
        val folders = diaryRepository.getFolders(userName).first()
        folderValidator.validateFolderName(newName, folders)
        diaryRepository.renameFolder(userName, oldName, newName)
    }

    override suspend fun addFolder(userName: String, folderName: String) {

        folderValidator.validateFolderName(folderName, emptyList())

        if (diaryRepository.folderExists(userName, folderName)) {
            throw IllegalArgumentException("이미 존재하는 폴더명입니다.")
        }

        diaryRepository.addFolder(userName, folderName)
    }
}
