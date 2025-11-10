package com.example.diaryapp.presentation.view

import com.example.diaryapp.port.InputPort

object InputView : InputPort{
    override fun readUserName(): String {
        println("아이디를 입력해주세요.")
        return readlnOrNull() ?: ""
    }

    override fun readPassword(): String {
        println("비밀번호를 입력해주세요")
        return readlnOrNull() ?: ""
    }

    override fun readTitle(): String {
        println("일기 제목을 입력해주세요.")
        return readlnOrNull() ?: ""
    }

    override fun readContent(): String {
        println("일기 내용을 입력해주세요")
        return readlnOrNull() ?: ""
    }
}