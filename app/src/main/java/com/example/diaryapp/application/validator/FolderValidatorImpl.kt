package com.example.diaryapp.application.validator

class FolderValidatorImpl : FolderValidator {
    override fun validateFolderName(
        name: String,
        existingFolders: List<String>
    ) {
        if (name.isBlank()) {
            throw IllegalArgumentException("폴더 이름을 입력해주세요.")
        }

        if (name.length > 20) {
            throw IllegalArgumentException("폴더 이름은 최대 20자까지 가능합니다.")
        }

        val invalidRegex = Regex("[\\\\/:*?\"<>|]")
        if (invalidRegex.containsMatchIn(name)) {
            throw IllegalArgumentException("폴더 이름에 특수문자를 포함할 수 없습니다.")
        }

        if (existingFolders.contains(name)) {
            throw IllegalArgumentException("이미 존재하는 폴더명입니다.")
        }
    }
}