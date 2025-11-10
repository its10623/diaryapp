package com.example.diaryapp.dto

import com.example.diaryapp.domain.model.User

data class UserDto(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val password: String,
)

fun User.toUserDto(): UserDto {
    return UserDto(
        this.id,
        this.name,
        this.password
    )
}

fun UserDto.toUser(): User {
    return User(
        this.id,
        this.name,
        this.password
    )
}
