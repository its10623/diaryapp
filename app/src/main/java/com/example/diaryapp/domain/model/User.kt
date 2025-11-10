package com.example.diaryapp.domain.model

data class User(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val password: String,
)