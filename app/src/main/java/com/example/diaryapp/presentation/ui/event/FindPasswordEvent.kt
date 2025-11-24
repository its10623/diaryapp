package com.example.diaryapp.presentation.ui.event

sealed class FindPasswordEvent {
    data class Success(val id: String) : FindPasswordEvent()
    data class Fail(val message: String) : FindPasswordEvent()
}