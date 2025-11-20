package com.example.diaryapp.presentation.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.presentation.ui.component.Dialog
import com.example.diaryapp.presentation.ui.component.EditorMode
import com.example.diaryapp.presentation.ui.screen.CalenderScreen
import com.example.diaryapp.presentation.ui.screen.DiaryEditorScreen
import com.example.diaryapp.presentation.ui.screen.DiaryViewScreen
import com.example.diaryapp.presentation.ui.screen.FolderScreen
import com.example.diaryapp.presentation.ui.screen.LoginScreen
import com.example.diaryapp.presentation.ui.screen.MainScreen
import com.example.diaryapp.presentation.ui.screen.ProfileScreen
import com.example.diaryapp.presentation.ui.screen.SignUpScreen
import com.example.diaryapp.presentation.ui.screen.findpassword.FindPasswordFlowScreen
import java.time.LocalDate

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    var showDeleteDialog by remember { mutableStateOf(false) }

    // 더미 데이터
    val dummyList = listOf(
        Diary(id = 1, folder = "여행", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 11일"),
        Diary(id = 2, folder = "학교", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 10일"),
        Diary(id = 3, folder = "일상", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 09일"),
        Diary(id = 4, folder = "여행", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 09일"),
        Diary(id = 5, folder = "학교", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 08일"),
        Diary(id = 6, folder = "일상", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 10일"),
        Diary(id = 9, folder = "여행", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 09일"),
        Diary(id = 10, folder = "일상", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 15일")
    )


    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onFindScreen = { navController.navigate(Screen.FindScreen.route) },
                onSignupScreen = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(Screen.FindScreen.route) {
            FindPasswordFlowScreen(
                onFindSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.FindScreen.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(navController,dummyList)
        }
        composable(Screen.WriteScreen.route) {
            DiaryEditorScreen(
                mode = EditorMode.CREATE,
                initialTitle = "",
                initialContent = "",
                initialDate = LocalDate.now().toString(),
                onSave = { _, _ -> navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getLong("id") ?: return@composable

            val diary = dummyList.firstOrNull { it.id ==id }
                ?: return@composable
            DiaryEditorScreen(
                mode = EditorMode.EDIT,
                initialTitle = diary.title,
                initialContent = diary.content,
                initialDate = diary.date,
                onSave = { _, _ -> navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "ViewDiary/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val diaryId = backStackEntry.arguments?.getLong("id") ?: return@composable
            DiaryViewScreen(
                diaryId = diaryId,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("edit/$diaryId") },
                onDelete = { showDeleteDialog = true }
            )
        }
    }
    // 삭제 여부 Dialog
    if (showDeleteDialog) {
        Dialog(
            title = "일기를 삭제하시겠습니까?",
            isTextField = false,
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                navController.popBackStack()
                showDeleteDialog = false
            }
        )
    }
}
