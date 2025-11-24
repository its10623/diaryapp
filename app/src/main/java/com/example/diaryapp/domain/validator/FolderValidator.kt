package com.example.diaryapp.domain.validator

interface FolderValidator {
    fun validateFolderName(name: String, existingFolders: List<String>)
}