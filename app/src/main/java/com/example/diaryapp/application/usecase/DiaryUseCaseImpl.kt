package com.example.diaryapp.application.usecase

import diaryapp.application.validator.DiaryValidator
import com.example.diaryapp.dto.toDiary
import com.example.diaryapp.domain.repository.DiaryRepository
import com.example.diaryapp.dto.DiaryDto
import com.example.diaryapp.dto.toDiaryDto
import java.util.NoSuchElementException

class DiaryUseCaseImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryValidator: DiaryValidator
) : DiaryUseCase {

    override fun onWrite(diaryDto: DiaryDto) {
        diaryValidator.validateTitleInput(diaryDto.title)
        diaryValidator.validateContentInput(diaryDto.content)

        val diary = diaryDto.toDiary()

        diaryRepository.saveDiary(diary)
    }

    override fun onModify(diaryDto: DiaryDto, newTitle: String, newContent: String) {
        diaryValidator.validateTitleInput(newTitle)
        diaryValidator.validateContentInput(newContent)

        val existingDiary = diaryRepository.findDiaryByUniqueTitle(diaryDto.name, diaryDto.title)
            ?: throw NoSuchElementException("수정할 일기(${diaryDto.title})를 찾을 수 없습니다.")

        if (existingDiary.name != diaryDto.name) {
            throw IllegalAccessException("본인 일기만 수정할 수 있습니다.")
        }

        val updatedDiary = existingDiary.copy(
            title = newTitle,
            content = newContent
        )

        diaryRepository.updateDiary(existingDiary.title, updatedDiary)
    }

    override fun onDelete(diaryDto: DiaryDto) {
        val existingDiary = diaryRepository.findDiaryByUniqueTitle(diaryDto.name, diaryDto.title)
            ?: throw NoSuchElementException("삭제할 다이어리가 존재하지 않습니다.")

        if (existingDiary.name != diaryDto.name) {
            throw IllegalAccessException("본인 일기만 삭제할 수 있습니다.")
        }
        diaryRepository.deleteDiary(diaryDto.name, diaryDto.title)
    }

    override fun findByUser(name: String): List<DiaryDto> {
        val diaryList = diaryRepository.findDiariesByUser(name)

        if (diaryList.isEmpty()) throw NoSuchElementException("${name}의 다이어리는 비어있습니다.")

        return diaryList.map { it.toDiaryDto() }
    }

    override fun findDiariesByTitle(name: String, titleKeyword: String): List<DiaryDto> {
        val diaryList = diaryRepository.findDiariesByTitle(name, titleKeyword)

        if (diaryList.isEmpty()) throw NoSuchElementException("입력한 키워드를 포함하는 일기를 찾을 수 없습니다.")

        return diaryList.map { it.toDiaryDto() }
    }

    override fun findDiariesByContent(name: String, contentKeyword: String): List<DiaryDto> {
        val diaryList = diaryRepository.findDiariesByContent(name, contentKeyword)

        if (diaryList.isEmpty()) throw NoSuchElementException("입력한 키워드를 포함하는 일기를 찾을 수 없습니다.")

        return diaryList.map { it.toDiaryDto() }
    }
}