package com.example.diaryapp.application.usecase

import com.example.diaryapp.dto.DiaryDto

interface DiaryUseCase {
    fun onWrite(diaryDto: DiaryDto)
    fun onDelete(diaryDto: DiaryDto)
    fun onModify(diaryDto: DiaryDto, newTitle: String, newContent: String)
    fun findByUser(name: String): List<DiaryDto>
    fun findDiariesByTitle(name: String, titleKeyword: String): List<DiaryDto>
    fun findDiariesByContent(name: String, contentKeyword: String): List<DiaryDto>
}