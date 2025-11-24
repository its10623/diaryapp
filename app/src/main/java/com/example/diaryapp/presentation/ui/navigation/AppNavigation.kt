package com.example.diaryapp.presentation.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diaryapp.presentation.ui.component.Dialog
import com.example.diaryapp.presentation.ui.component.EditorMode
import com.example.diaryapp.presentation.ui.screen.DiaryEditorScreen
import com.example.diaryapp.presentation.ui.screen.DiaryViewScreen
import com.example.diaryapp.presentation.ui.screen.LoginScreen
import com.example.diaryapp.presentation.ui.screen.MainScreen
import com.example.diaryapp.presentation.ui.screen.SignUpScreen
import com.example.diaryapp.presentation.ui.screen.findpassword.FindPasswordFlowScreen
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel
import com.example.diaryapp.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val diaryViewModel: DiaryViewModel = hiltViewModel()

    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current // Toast를 위해 Context 가져오기

    var currentUserName by remember { mutableStateOf<String?>(null) }

    val pendingDeleteId by diaryViewModel.pendingDeleteId.collectAsState()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val userName = currentBackStack
        ?.arguments
        ?.getString("userName") ?: ""
    
    // 삭제 성공/실패 피드백
    LaunchedEffect(Unit) {
        diaryViewModel.deleteSuccess.collectLatest { success ->
            if (success) {
                Toast.makeText(context, "일기가 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                // 현재 화면이 상세보기라면 뒤로가기
                val route = navController.currentBackStackEntry
                    ?.destination
                    ?.route
                if (route?.startsWith("ViewDiary/") == true) {
                    navController.popBackStack()
                }

                diaryViewModel.clearPendingDelete()
            }
        }
    }

    LaunchedEffect(pendingDeleteId) {
        if (pendingDeleteId != null) {
            showDeleteDialog = true
        } else {
            showDeleteDialog = false
        }
    }

    // 삭제 여부 Dialog
    if (showDeleteDialog && pendingDeleteId != null) {
        Dialog(
            title = "일기를 삭제하시겠습니까?",
            isTextField = false,
            onDismiss = {
                diaryViewModel.clearPendingDelete()
                showDeleteDialog = false
            },
            onConfirm = {
                pendingDeleteId?.let { idToDelete ->
                    diaryViewModel.deleteDiary(idToDelete, currentUserName.orEmpty())
                    showDeleteDialog = false
                    diaryViewModel.clearPendingDelete()
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        diaryViewModel.errorMessage.collectLatest { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                diaryViewModel.clearErrorMessage() // 에러 메시지 표시 후 초기화
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val context = LocalContext.current
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { userName ->
                    navController.navigate(Screen.Main.route + "/$userName") {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onFindScreen = { navController.navigate(Screen.FindScreen.route) },
                onSignupScreen = { navController.navigate(Screen.SignUp.route) },
                showToast = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
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
            val context = LocalContext.current
            SignUpScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                },
                showToast = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            )
        }

        composable(
            route = Screen.Main.route + "/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStack ->
            val userName = backStack.arguments?.getString("userName") ?: return@composable

            LaunchedEffect(userName) {
                currentUserName = userName
            }

            MainScreen(
                appNavController = navController,
                userName = userName,
                diaryViewModel = diaryViewModel,
                loginViewModel = loginViewModel,
            )
        }

        composable(
            route = Screen.WriteScreen.route,
            arguments = listOf(
                navArgument("folder") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val userName =
                navController.previousBackStackEntry?.arguments?.getString("userName") ?: ""
            val folderName = backStackEntry.arguments?.getString("folder")

            DiaryEditorScreen(
                mode = EditorMode.CREATE,
                id = null,
                userName = userName,
                folderName = folderName,
                viewModel = diaryViewModel,
                onSave = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val userName =
                navController.previousBackStackEntry?.arguments?.getString("userName") ?: ""

            DiaryEditorScreen(
                id = id,
                mode = EditorMode.EDIT,
                userName = userName,
                folderName = null,
                viewModel = diaryViewModel,
                onSave = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ViewDiary.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")!!
            val userName =
                navController.previousBackStackEntry?.arguments?.getString("userName") ?: ""

            DiaryViewScreen(
                id = id,
                userName = userName,
                viewModel = diaryViewModel,
                onBack = { navController.popBackStack() },
                onEdit = { diaryId -> navController.navigate(Screen.EditScreen.route.replace("{id}", diaryId.toString())) },
                onDeleteRequest = { diaryId ->
                    diaryViewModel.prepareDelete(diaryId) }
            )
        }
    }
}