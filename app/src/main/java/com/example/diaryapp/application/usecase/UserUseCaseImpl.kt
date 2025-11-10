package com.example.diaryapp.application.usecase

import com.example.diaryapp.application.validator.InputValidator
import com.example.diaryapp.domain.repository.UserRepository
import com.example.diaryapp.dto.UserDto
import com.example.diaryapp.dto.toUser
import com.example.diaryapp.dto.toUserDto
import org.mindrot.jbcrypt.BCrypt

class UserUseCaseImpl(
    private val userRepository: UserRepository,
    private val inputValidator: InputValidator
) : UserUseCase {

    override fun signUpUseCase(userDto: UserDto) {
        inputValidator.validateUserName(userDto.name)
        inputValidator.validatePassword(userDto.password)

        if (userRepository.isUserExists(userDto.name)) {
            throw IllegalArgumentException("이미 존재하는 사용자 입니다.")
        }
        val newUser = userDto.toUser()
        userRepository.saveUser(newUser)
    }

    override fun loginUseCase(userDto: UserDto): UserDto {
        inputValidator.validateUserName(userDto.name)
        inputValidator.validatePassword(userDto.password)

        val user = userRepository.loadUser(userDto.name)
            ?: throw IllegalArgumentException("사용자 이름 또는 비밀번호가 잘못되었습니다.")
        if (!BCrypt.checkpw(userDto.password, user.password)) {
            throw IllegalArgumentException("사용자 이름 또는 비밀번호가 잘못되었습니다.")
        }
        return user.toUserDto()
    }
}