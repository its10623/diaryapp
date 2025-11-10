package com.example.diaryapp.port

interface InputPort {
    fun readUserName(): String
    fun readPassword(): String
    fun readTitle(): String
    fun readContent(): String
}