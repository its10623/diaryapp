package com.example.diaryapp.application.validator

object DiaryInputValidator : DiaryValidator {
    override fun validateTitleInput(title: String) {
        validateTextBlank(title, TITLE_EMPTY)
        validateTitleRange(title, TITLE_RANGE)
        validateTitleFormat(title, TITLE_FORMAT)
    }

    override fun validateContentInput(content: String) {
        validateTextBlank(content, CONTENT_EMPTY)
        validateContentRange(content, CONTENT_RANGE)
        validateContentControlChars(content, CONTENT_CONTROL_CHARS)
    }

    private fun validateTextBlank(input: String, error: String) {
        if (input.isBlank()) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateTitleRange(title: String, error: String) {
        if (title.length > 50) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateTitleFormat(title: String, error: String) {
        val pattern = Regex("""[\\/:*?"<>|]""")
        if (pattern.containsMatchIn(title)) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateContentRange(content: String, error: String) {
        if (content.length > 5000) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateContentControlChars(content: String, error: String) {
        val controlChars = Regex("""[\u0000-\u001F\u007F]""")
        if (controlChars.containsMatchIn(content))
            throw IllegalArgumentException(error)
    }

    private const val TITLE_EMPTY = "제목은 비어있을 수 없습니다."
    private const val TITLE_CONTAINS_BLANK = "제목은 공백이 포함될 수 없습니다."
    private const val TITLE_RANGE = "제목의 길이는 50자 이하로 입력해주세요."
    private const val TITLE_FORMAT = "제목은 특수문자를 포함할 수 없습니다."

    private const val CONTENT_EMPTY = "본문은 비어있을 수 없습니다."
    private const val CONTENT_RANGE = "본문은 5000자 이하로 입력해주세요."
    private const val CONTENT_CONTROL_CHARS = "본문에 허용되지 않는 제어문자가 포함되어 있습니다."
}