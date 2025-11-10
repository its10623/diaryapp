package com.example.diaryapp.application.validator

object UserInputValidator : InputValidator {
    override fun validateUserName(input: String) {
        validateBlank(input, NAME_BLANK)
        validateContainsBlank(input, NAME_CONTAINS_BLANK)
        validateLength(input, NAME_LENGTH)
        validateSpecialCharacters(input, NAME_SPECIAL_CHARACTERS)
    }

    override fun validatePassword(input: String) {
        validateBlank(input, PW_BLANK)
        validateContainsBlank(input, PW_CONTAINS_BLANK)
        validateLength(input, PW_LENGTH)
        validatePwSpecialCharacters(input, PW_NOT_SPECIAL_CHARACTERS)
    }

    // 입력 공백 검증
    private fun validateBlank(input: String, error: String) {
        if (input.isBlank()) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validateContainsBlank(input: String, error: String) {
        if (input.contains(" ")) {
            throw IllegalArgumentException(error)
        }
    }

    // 입력 길이 예외 검증
    private fun validateLength(input: String, error: String) {
        val inputLength = input.length
        if (inputLength !in 8..16) {
            throw IllegalArgumentException(error)
        }
    }

    // 사용자 이름에 특수문자가 있을 경우 예외 검증
    private fun validateSpecialCharacters(input: String, error: String) {
        val specialCharacters = input.filter { !it.isLetterOrDigit() }
        if (specialCharacters.isNotEmpty()) {
            throw IllegalArgumentException(error)
        }
    }

    private fun validatePwSpecialCharacters(input: String, error: String) {
        val specialCharacters = input.filter { !it.isLetterOrDigit() }
        if (specialCharacters.isEmpty()) {
            throw IllegalArgumentException(error)
        }
    }

    private const val NAME_BLANK = "사용자 이름을 입력해주세요."
    private const val NAME_CONTAINS_BLANK = "사용자 이름은 공백을 포함할 수 없습니다."
    private const val NAME_LENGTH = "사용자 이름은 8~16자로 입력해주세요."
    private const val NAME_SPECIAL_CHARACTERS = "사용자 이름은 특수문자를 사용할 수 없습니다."

    private const val PW_BLANK = "비밀번호를 입력해주세요."
    private const val PW_CONTAINS_BLANK = "비밀번호는 공백을 포함할 수 없습니다."
    private const val PW_LENGTH = "비밀번호는 8~16자로 입력해주세요."
    private const val PW_NOT_SPECIAL_CHARACTERS = "비밀번호는 특수문자를 포함해야 합니다."
}