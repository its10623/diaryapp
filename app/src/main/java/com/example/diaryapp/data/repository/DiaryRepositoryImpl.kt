package com.example.diaryapp.data.repository

import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.domain.repository.DiaryRepository
import com.example.diaryapp.presentation.FakeContext
import java.io.File
import kotlin.String

class DiaryRepositoryImpl(
    private val context: FakeContext
) : DiaryRepository {

    private val diaryDir = File(context.filesDir, "diaries")

    init {
        if (!diaryDir.exists()) diaryDir.mkdirs()
    }

    override fun saveDiary(diary: Diary) {
        val userDir = File(diaryDir, diary.name)
        if (!userDir.exists()) userDir.mkdirs()

        val uniqueFilenameTitle = makeUniqueTitle(userDir, diary.title)

        val userDiary = File(userDir, "${uniqueFilenameTitle}.txt")
        userDiary.writeText("${diary.date}\n${uniqueFilenameTitle}\n${diary.content}\n")
    }

    override fun updateDiary(oldTitle: String, updatedDiary: Diary) {
        val userDir = File(diaryDir, updatedDiary.name)
        if (!userDir.exists()) return

        val oldFile = userDir.listFiles { file -> file.extension == "txt" && file.nameWithoutExtension == oldTitle }
            ?.firstOrNull()

        if (oldFile != null && oldFile.exists()) {
            oldFile.delete()
        } else {
            throw NoSuchElementException("수정할 일기($oldTitle)를 찾을 수 없습니다.")
        }

        saveDiary(updatedDiary)
    }

    fun makeUniqueTitle(userDir: File, title: String): String {
        var newTitle = title
        var counter = 1

        while (File(userDir, "$newTitle.txt").exists()) {
            newTitle = "$title ($counter)"
            counter++
        }
        return newTitle
    }

    override fun deleteDiary(name: String, title: String) {
        val file = File(File(diaryDir, name), "$title.txt")
        if (file.exists()) file.delete()
    }

    override fun findDiariesByUser(name: String): List<Diary> {
        val userDir = File(diaryDir, name)
        if (!userDir.exists()) return emptyList()

        return userDir.listFiles { file -> file.extension == "txt" }
            ?.map { file ->
                val lines = file.readLines()
                Diary(
                    name = name,
                    date = lines.getOrNull(0) ?: "",
                    title = file.nameWithoutExtension,
                    content = lines.drop(2).joinToString("\n")
                )
            }?.sortedByDescending { it.date } ?: emptyList()
    }

    override fun findDiariesByTitle(name: String, titleKeyword: String): List<Diary> {
        val userDir = File(diaryDir, name)
        if (!userDir.exists() || !userDir.isDirectory) return emptyList()

        val diaryFiles = userDir.listFiles { file -> file.extension == "txt" } ?: return emptyList()

        return diaryFiles.mapNotNull { file ->
            val lines = file.readLines()
            val fileTitle = lines.getOrNull(1)?.trim()

            if (fileTitle != null && fileTitle.contains(titleKeyword, ignoreCase = true)) {
                Diary(
                    name = name,
                    date = lines.getOrNull(0) ?: "",
                    title = file.nameWithoutExtension,
                    content = lines.drop(2).joinToString("\n")
                )
            } else {
                null
            }
        }.sortedByDescending { it.date }
    }

    override fun findDiariesByContent(name: String, contentKeyword: String): List<Diary> {
        val userDir = File(diaryDir, name)
        if (!userDir.exists() || !userDir.isDirectory) return emptyList()

        val diaryFiles = userDir.listFiles { file -> file.extension == "txt" } ?: return emptyList()

        return diaryFiles.mapNotNull { file ->
            val lines = file.readLines()
            val fileContent = lines.getOrNull(2)?.trim()

            if (fileContent != null && fileContent.contains(contentKeyword, ignoreCase = true)) {
                Diary(
                    name = name,
                    date = lines.getOrNull(0) ?: "",
                    title = file.nameWithoutExtension,
                    content = lines.drop(2).joinToString("\n")
                )
            } else {
                null
            }
        }.sortedByDescending { it.date }
    }

    override fun findDiaryByUniqueTitle(name: String, uniqueTitle: String): Diary? {
        val userDir = File(diaryDir, name)
        if (!userDir.exists() || !userDir.isDirectory) return null

        val diaryFile = userDir.listFiles { file ->
            file.extension == "txt" && file.nameWithoutExtension == uniqueTitle
        }?.firstOrNull() ?: return null

        val lines = diaryFile.readLines()
        return Diary(
            name = name,
            date = lines.getOrNull(0) ?: "",
            title = diaryFile.nameWithoutExtension,
            content = lines.drop(2).joinToString("\n")
        )
    }
}
