package com.example.diaryapp.application.validator

interface FolderValidator {
    fun validateFolderName(name: String, existingFolders: List<String>)
}