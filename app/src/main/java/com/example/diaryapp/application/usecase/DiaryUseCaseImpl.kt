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

    override fun searchInFolder(userName: String, folder: String, keyword: String): Flow<List<DiaryDto>> =
        diaryRepository.searchInFolder(userName, folder, keyword)

    override fun searchTimeline(userName: String, keyword: String): Flow<List<DiaryDto>> =
        diaryRepository.searchTimeline(userName, keyword)

    override fun filterByDate(userName: String, start: Long, end: Long): Flow<List<DiaryDto>> =
        diaryRepository.filterByDate(userName, start, end)

    override fun getFolders(userName: String): Flow<List<String>> =
        diaryRepository.getFolders(userName)

    override suspend fun renameFolder(userName: String, oldName: String, newName: String) {
        val folders = diaryRepository.getFolders(userName).first()
        // folderValidator.validateFolderName(newName, folders) // Assuming validator checks the new name
        diaryRepository.renameFolder(userName, oldName, newName)
    }
}
