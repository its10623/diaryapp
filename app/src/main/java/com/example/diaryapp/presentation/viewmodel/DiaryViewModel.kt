package com.example.diaryapp.presentation.viewmodel

import com.example.diaryapp.dto.DiaryDto

interface DiaryViewModel {
    fun writeDiary(diaryDto: DiaryDto)
    fun deleteDiary(diaryDto: DiaryDto)
    fun modifyDiary(diaryDto: DiaryDto, newTitle: String, newContent: String)
    fun findUserDiaries(name: String): List<DiaryDto>
    fun findDiariesByTitle(name: String, titleKeyword: String): List<DiaryDto>
    fun findDiariesByContent(name: String, contentKeyword: String): List<DiaryDto>
}