package com.example.diaryapp.domain.repository

import com.example.diaryapp.domain.model.User

interface UserRepository {
    fun loadUser(name: String): User?
    fun saveUser(user: User)
    fun deleteUser(name: String): Boolean
    fun isUserExists(name: String): Boolean
}