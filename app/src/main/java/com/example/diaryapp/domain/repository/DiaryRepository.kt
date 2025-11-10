package com.example.diaryapp.domain.repository

import com.example.diaryapp.domain.model.Diary

interface DiaryRepository {
    fun saveDiary(diary: Diary)
    fun deleteDiary(name: String, title: String)
    fun findDiariesByUser(name: String): List<Diary>
    fun findDiariesByTitle(name: String, titleKeyword: String): List<Diary>
    fun findDiariesByContent(name: String, contentKeyword: String): List<Diary>
    fun updateDiary(oldTitle: String, updatedDiary: Diary)
    fun findDiaryByUniqueTitle(name: String, uniqueTitle: String): Diary?
}