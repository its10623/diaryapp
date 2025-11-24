package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.presentation.ui.component.Dialog
import com.example.diaryapp.presentation.ui.component.DrawerContent
import com.example.diaryapp.presentation.ui.component.FilterBottomSheet
import com.example.diaryapp.presentation.ui.component.SortType
import com.example.diaryapp.presentation.ui.component.button.WriteFab
import com.example.diaryapp.presentation.ui.navigation.BottomNavBar
import com.example.diaryapp.presentation.ui.navigation.Screen
import com.example.diaryapp.presentation.ui.navigation.bottomNavScreen
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.BoundaryLine
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    val folderList = remember {
        mutableStateListOf(
            "모든 일기",
            "여행",
            "학교",
            "일상",
        )
    }

    var showAddFolderDialog by remember { mutableStateOf(false) }

    // 검색
    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 검색 관련
    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    // 필터 상태
    var selectedDates by remember { mutableStateOf(setOf<LocalDate>()) }
    var sortType by remember { mutableStateOf(SortType.RECENT) }
    var filterSheetVisible by remember { mutableStateOf(false) }

    // FAB 스크롤 상태
    val listState = rememberLazyListState()

    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    // 날짜 리스트 (바텀시트에 넘길용)
    val diaryDates = remember(dummyList) {
        dummyList.map { LocalDate.parse(it.date, formatter) }
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
                    folders = folderList,
                    onFolderClick = { folder ->
                        query = ""
                        scope.launch { drawerState.close() }
                        bottomNavController.navigate("folder/$folder") {
                            launchSingleTop = true
                        }
                    },
                    onAddFolder = {
                        scope.launch {
                            drawerState.close()
                            showAddFolderDialog = true
                        }
                    },
                    onFavorite = {
                        scope.launch { drawerState.close() }
                        /*TODO 즐겨찾기*/
                    },
                    onTrashed = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onLogout = {
                        scope.launch { drawerState.close() }
                        /*TODO 로그아웃*/
                    },
                    onSettings = {
                        scope.launch { drawerState.close() }
                        /*TODO 설정*/
                    }
                )
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                if (currentRoute in listOf(
                        Screen.Bottom.Timeline.route,
                        Screen.Bottom.Calender.route,
                        Screen.Bottom.Profile.route,
                    ) || currentRoute?.startsWith("folder/") == true
                ) {
                    WriteFab(
                        listState = listState,
                        onWrite = {
                            appNavController.navigate(Screen.WriteScreen.route)
                        }
                    )
                }
            },
            bottomBar = {
                if (bottomNavScreen.isNotEmpty()) {
                    NavigationBar(
                        containerColor = PrimaryAccent,
                    ) {
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
                    }
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
                    val timelineDiaries = remember(query, selectedDates, sortType) {
                        dummyList
                            .filter { diary ->
                                if (query.isBlank()) true
                                else diary.title.contains(query) || diary.content.contains(query)
                            }
                            .filter { diary ->
                                if (selectedDates.isEmpty()) {
                                    true
                                } else {
                                    LocalDate.parse(diary.date, formatter) in selectedDates
                                }
                            }
                            .let { list ->
                                when (sortType) {
                                    SortType.NONE -> list
                                    SortType.RECENT -> list.sortedByDescending { d ->
                                        LocalDate.parse(
                                            d.date, formatter
                                        )
                                    }

                                    SortType.OLD -> list.sortedBy { d ->
                                        LocalDate.parse(d.date, formatter)
                                    }
                                }
                            }
                    }

                    TimelineScreen(
                        userName = userName,
                        viewModel = diaryViewModel,
                        onFilterClick = {
                            diaryViewModel.setFilterScope(FilterScope.TIMELINE)
                            filterSheetVisible = true
                        },
                        onSearchOpen = { searchVisible = true },
                        onFilterClick = { filterSheetVisible = true },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onViewDiary = { id ->
                            appNavController.navigate("ViewDiary/$id")
                        },
                        onEdit = { id -> appNavController.navigate("edit/$id") },
                        onDelete = { showDeleteDialog = true },
                    )
                }
                composable("folder/{folder}") { backStackEntry ->
                    val folderName = backStackEntry.arguments?.getString("folder") ?: "모든 일기"

                    val folderDiaries = remember(folderName, query, selectedDates, sortType) {
                        dummyList
                            .filter {
                                if (folderName == "모든 일기") true
                                else it.folder == folderName
                            }
                            .filter { diary ->
                                if (query.isBlank()) true
                                else diary.title.contains(query) || diary.content.contains(query)
                            }
                            .filter { diary ->
                                if (selectedDates.isEmpty()) {
                                    true
                                } else {
                                    LocalDate.parse(diary.date, formatter) in selectedDates
                                }
                            }
                            .let { list ->
                                when (sortType) {
                                    SortType.NONE -> list
                                    SortType.RECENT -> list.sortedByDescending { d ->
                                        LocalDate.parse(
                                            d.date, formatter
                                        )
                                    }

                                    SortType.OLD -> list.sortedBy { d ->
                                        LocalDate.parse(d.date, formatter)
                                    }
                                }
                            }
                    }

                    FolderScreen(
                        folderName = folderName,
                        viewModel = diaryViewModel,
                        onFilterClick = {
                            diaryViewModel.setFilterScope(FilterScope.FOLDER)
                            filterSheetVisible = true
                        },
                        onFilterClick = { filterSheetVisible = true },
                        onViewDiary = { id ->
                            appNavController.navigate("ViewDiary/$id")
                        },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onEdit = { id -> appNavController.navigate("edit/$id") },
                        onDelete = { showDeleteDialog = true }
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
                            folderList.add(newFolder)
                        }
                        showAddFolderDialog = false
                    }
                )
            }
            if (showDeleteDialog) {
                Dialog(
                    title = "일기를 삭제하시겠습니까?",
                    isTextField = false,
                    onDismiss = { showDeleteDialog = false },
                    onConfirm = { newFolder ->
                        if (newFolder.isNotBlank()) {
                            folderList.add(newFolder)
                        }
                        showDeleteDialog = false
                    }
                )
            }
        }
    }
}
