package com.example.diaryapp.presentation.viewmodel

import com.example.diaryapp.application.usecase.DiaryUseCase
import com.example.diaryapp.dto.DiaryDto
import java.util.NoSuchElementException

class DiaryViewModelImpl(
    private val diaryUseCase: DiaryUseCase
): DiaryViewModel {
    override fun writeDiary(diaryDto: DiaryDto) {
        try {
            diaryUseCase.onWrite(diaryDto)
            println("일기가 저장되었습니다.")
        } catch (e: IllegalArgumentException) {
            println("저장 실패: ${e.message}")
        }
    }

    override fun modifyDiary(diaryDto: DiaryDto, newTitle: String, newContent: String) {
        try {
            diaryUseCase.onModify(diaryDto, newTitle, newContent)
            println("일기가 수정되었습니다.")
        } catch (e: Exception) {
            println("저장 실패: ${e.message}")
        }
    }

    override fun deleteDiary(diaryDto: DiaryDto) {
        try {
            diaryUseCase.onDelete(diaryDto)
            println("삭제가 완료되었습니다.")
        } catch (e: Exception) {
            println("삭제 실패: ${e.message}")
        }
    }

    override fun findUserDiaries(name: String): List<DiaryDto> {
        return try {
            val diaries = diaryUseCase.findByUser(name)
            diaries.forEach { diary ->
                println("날짜: ${diary.date}\n제목: ${diary.title}\n내용: ${diary.content}\n")
            }
            diaries
        } catch (e: Exception) {
            println("조회 실패: ${e.message}")
            emptyList()
        }
    }

    override fun findDiariesByTitle(name: String, titleKeyword: String): List<DiaryDto> {
        return try {
            val diaries = diaryUseCase.findDiariesByTitle(name, titleKeyword)
            diaries.forEach { diary ->
                println("날짜: ${diary.date}\n제목: ${diary.title}\n내용: ${diary.content}\n")
            }
            diaries
        } catch (e: NoSuchElementException) {
            println("조회 실패: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            println("조회 실패: ${e.message}")
            emptyList()
        }
    }

    override fun findDiariesByContent(name: String, contentKeyword: String): List<DiaryDto> {
        return try {
            val diaries = diaryUseCase.findDiariesByContent(name, contentKeyword)
            diaries.forEach { diary ->
                println("날짜: ${diary.date}\n제목: ${diary.title}\n내용: ${diary.content}\n")
            }
            diaries
        } catch (e: NoSuchElementException) {
            println("조회 실패: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            println("조회 실패: ${e.message}")
            emptyList()
        }
    }
}
