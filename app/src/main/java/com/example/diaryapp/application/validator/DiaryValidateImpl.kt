package com.example.diaryapp.application.validator

import javax.inject.Inject

class DiaryValidateImpl : DiaryValidator {
    override fun validateTitleInput(title: String) {
        validateTextBlank(title, TITLE_EMPTY)
        validateTitleRange(title, TITLE_RANGE)
    }

    override fun validateContentInput(content: String) {
        validateTextBlank(content, CONTENT_EMPTY)
    }

    private fun validateTextBlank(input: String, error: String) {
        if (input.isBlank()) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateTitleRange(title: String, error: String) {
        if (title.length > 20) {
            throw IllegalArgumentException(error)
        }
    }

    private companion object {
        private const val TITLE_EMPTY = "제목을 입력해주세요"
        private const val TITLE_RANGE = "제목의 길이는 20자 이하로 입력해주세요."
        private const val CONTENT_EMPTY = "본문은 비어있을 수 없습니다."
    }
}