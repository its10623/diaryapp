package com.example.diaryapp.presentation.ui.screen


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diaryapp.presentation.ui.component.timeline.Dialog
import com.example.diaryapp.presentation.ui.component.timeline.DrawerContent
import com.example.diaryapp.presentation.ui.component.timeline.FilterBottomSheet
import com.example.diaryapp.presentation.ui.component.timeline.SortType
import com.example.diaryapp.presentation.ui.component.button.WriteFab
import com.example.diaryapp.presentation.ui.component.timeline.BottomNavBar
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.BoundaryLine
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel
import com.example.diaryapp.presentation.viewmodel.FilterScope
import com.example.diaryapp.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@Composable
fun MainScreen(
    appNavController: NavHostController,
    userName: String,
    diaryViewModel: DiaryViewModel,
    loginViewModel: LoginViewModel,
) {
    val bottomNavController = rememberNavController()
    val currentBackStack by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    var showAddFolderDialog by remember { mutableStateOf(false) }

    // 검색
    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 필터 상태
    var filterSheetVisible by remember { mutableStateOf(false) }

    // FAB 스크롤 상태
    val listState = rememberLazyListState()

    val folders by diaryViewModel.folders.collectAsState()
    LaunchedEffect(Unit) {
        diaryViewModel.loadFolders(userName)
        diaryViewModel.loadUserDiaries(userName)
    }

    var showExitDialog by remember { mutableStateOf(false) }

    // 뒤로가기 시 처리
    BackHandler(enabled = currentRoute == Screen.Bottom.Timeline.route) {
        showExitDialog = true
    }
    val context = LocalContext.current

    if (showExitDialog) {
        Dialog(
            title = "앱을 종료하시겠습니까?",
            isTextField = false,
            onDismiss = { showExitDialog = false },
            onConfirm = {
                showExitDialog = false
                (context as Activity).finish()
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                tonalElevation = 4.dp,
                color = BackGround,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .border(
                        width = 2.dp,
                        color = BoundaryLine,
                        shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                    ),
            ) {
                DrawerContent(
                    folders = folders,
                    onClose = { scope.launch { drawerState.close() } },
                    onWriteDiary = {
                        scope.launch { drawerState.close() }
                        diaryViewModel.clearSelected()
                        appNavController.navigate("editor")
                    },
                    onFolderClick = { folder ->
                        query = ""
                        scope.launch { drawerState.close() }
                        if (folder == "즐겨찾기") {
                            bottomNavController.navigate(Screen.Favorites.route) {
                                popUpTo(Screen.Bottom.Timeline.route) {
                                    inclusive = false
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } else {
                            bottomNavController.navigate("folder/$folder") {
                                popUpTo("folder/{folder}") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    onAddFolder = {
                        scope.launch {
                            drawerState.close()
                            showAddFolderDialog = true
                        }
                    },
                    onTrashed = {
                        scope.launch {
                            drawerState.close()
                            // 미 구현
                        }
                    },
                    onLogout = {
                        scope.launch {
                            loginViewModel.logout()
                            appNavController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    onSettings = {
                        scope.launch { drawerState.close() }
                        // 미 구현
                    }
                )
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                if (currentRoute?.startsWith("folder/") == true ||
                    currentRoute in listOf(
                        Screen.Bottom.Timeline.route,
                        Screen.Bottom.Calender.route,
                        Screen.Bottom.Profile.route
                    )
                ) {
                    WriteFab(
                        listState = listState,
                        onWrite = {
                            diaryViewModel.clearSelected()
                            val currentScreenRoute =
                                bottomNavController.currentBackStackEntry?.destination?.route
                            if (currentScreenRoute?.startsWith("folder/") == true) {
                                val folderName =
                                    bottomNavController.currentBackStackEntry?.arguments?.getString(
                                        "folder"
                                    ) ?: ""
                                appNavController.navigate("editor?folder=$folderName")
                            } else {
                                appNavController.navigate("editor")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (bottomNavScreen.isNotEmpty()) {
                    BottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            if (currentRoute == route) return@BottomNavBar
                            if (route == Screen.Bottom.Timeline.route) {
                                bottomNavController.popBackStack()
                            } else {
                                bottomNavController.navigate(route) {
                                    popUpTo(bottomNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.height(0.dp))
                }
            },

            ) { paddingValues ->

            if (filterSheetVisible) {

                val filterScope by diaryViewModel.currentFilterScope.collectAsState()

                // 스코프에 따라 다른 상태를 읽음
                val sortType by when (filterScope) {
                    FilterScope.TIMELINE -> diaryViewModel.timelineSortType.collectAsState()
                    FilterScope.FOLDER -> diaryViewModel.folderSortType.collectAsState()
                }

                val selectedDates by when (filterScope) {
                    FilterScope.TIMELINE -> diaryViewModel.timelineDates.collectAsState()
                    FilterScope.FOLDER -> diaryViewModel.folderDates.collectAsState()
                }

                val diaryList by when (filterScope) {
                    FilterScope.TIMELINE -> diaryViewModel.filteredDiaryList.collectAsState()
                    FilterScope.FOLDER -> diaryViewModel.filteredFolderList.collectAsState()
                }

                // 날짜 포맷 변환
                val diaryDates = remember(diaryList) {
                    diaryList.map { diary ->
                        Instant.ofEpochMilli(diary.createDate.time)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                }
                FilterBottomSheet(
                    sortType = sortType,
                    selectedDates = selectedDates,
                    diaryDates = diaryDates,
                    onApply = { newSort, newDates ->
                        diaryViewModel.applyFilter(newSort, newDates)
                        filterSheetVisible = false
                    },
                    onDismiss = {
                        filterSheetVisible = false
                    },
                    onReset = {
                        diaryViewModel.applyFilter(SortType.NONE, emptySet())
                        filterSheetVisible = false
                    }
                )
            }

            NavHost(
                navController = bottomNavController,
                startDestination = Screen.Bottom.Timeline.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Bottom.Timeline.route) {
                    TimelineScreen(
                        userName = userName,
                        viewModel = diaryViewModel,
                        onFilterClick = {
                            diaryViewModel.setFilterScope(FilterScope.TIMELINE)
                            filterSheetVisible = true
                        },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onViewDiary = { id ->
                            appNavController.navigate("ViewDiary/$id")
                        },
                        onEdit = { id ->
                            appNavController.navigate(
                                Screen.EditScreen.route.replace(
                                    "{id}",
                                    id.toString()
                                )
                            )
                        },
                        onDeleteRequest = { id ->
                            diaryViewModel.prepareDelete(id)
                        },
                    )
                }
                composable(
                    route = "folder/{folder}",
                    arguments = listOf(
                        navArgument("folder") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val folderName =
                        backStackEntry.arguments?.getString("folder") ?: ""

                    FolderScreen(
                        userName = userName,
                        folderName = folderName,
                        viewModel = diaryViewModel,
                        onFilterClick = {
                            diaryViewModel.setFilterScope(FilterScope.FOLDER)
                            filterSheetVisible = true
                        },
                        onViewDiary = { id ->
                            appNavController.navigate("ViewDiary/$id")
                        },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onEdit = { id ->
                            appNavController.navigate(
                                Screen.EditScreen.route.replace(
                                    "{id}",
                                    id.toString()
                                )
                            )
                        },
                        onDeleteRequest = { id ->
                            diaryViewModel.prepareDelete(id)
                        }
                    )
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        userName = userName,
                        viewModel = diaryViewModel,
                        onFilterClick = { filterSheetVisible = true },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onViewDiary = { id ->
                            appNavController.navigate("ViewDiary/$id")
                        },
                        onEdit = { id ->
                            appNavController.navigate(
                                Screen.EditScreen.route.replace(
                                    "{id}",
                                    id.toString()
                                )
                            )
                        },
                        onDeleteRequest = { id ->
                            diaryViewModel.prepareDelete(id)
                        }
                    )
                }
                composable(Screen.Bottom.Calender.route) {
                    CalenderScreen(
                        onNavigate = { route ->
                            bottomNavController.navigate(route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(Screen.Bottom.Profile.route) {
                    ProfileScreen(
                        onNavigate = { route ->
                            bottomNavController.navigate(route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            if (showAddFolderDialog) {
                Dialog(
                    title = "새 폴더 생성",
                    label = "폴더 이름",
                    confirmText = "생성",
                    onDismiss = { showAddFolderDialog = false },
                    onConfirm = { newFolder ->
                        if (newFolder.isNotBlank()) {
                            diaryViewModel.addFolder(userName, newFolder)
                        }
                        showAddFolderDialog = false
                    }
                )
            }
        }
    }
}