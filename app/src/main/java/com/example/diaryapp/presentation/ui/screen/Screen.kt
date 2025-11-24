package com.example.diaryapp.presentation.ui.screen

import com.example.diaryapp.R

sealed class Screen(
    val route: String
) {
    sealed class Bottom(val bottomRoute: String, val title: String, val icon: Int) : Screen(bottomRoute) {
        object Timeline : Bottom("timeline","타임라인",R.drawable.ic_timeline)
        object Calender : Bottom("calender", "달력", R.drawable.ic_calendar_month_24dp_e3e3e3_fill0_wght400_grad0_opsz24)
        object Profile : Bottom("profile","프로필",R.drawable.ic_account_circle_24dp_e3e3e3_fill0_wght400_grad0_opsz24)
    }

    object Main : Screen("main")
    object Login : Screen("login")
    object FindScreen : Screen("findScreen")
    object SignUp : Screen("signup")
    object FolderScreen : Screen("folderScreen")
    object WriteScreen : Screen("editor?folder={folder}")
    object EditScreen : Screen("edit/{id}") // 기존 일기 수정 경로로 수정
    object ViewDiary : Screen("ViewDiary/{id}")
    object Favorites : Screen("favorites_screen")
}

val bottomNavScreen = listOf(
    Screen.Bottom.Timeline,
    Screen.Bottom.Calender,
    Screen.Bottom.Profile
)
