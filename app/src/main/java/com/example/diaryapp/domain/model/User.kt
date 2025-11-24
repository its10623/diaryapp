package com.example.diaryapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = System.currentTimeMillis(),
    val userName: String,
    val password: String,
)