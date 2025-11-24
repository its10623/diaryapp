package com.example.diaryapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.example.diaryapp.domain.model.Diary

@Composable
fun TimelineScreen(
    diaryList: List<Diary>,
    searchVisible: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchOpen: () -> Unit,
    onSearchClose: () -> Unit,
    onViewDiary: (Long) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    val diaries by viewModel.filteredDiaryList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query.isBlank()) {
            if (!viewModel.activeFilter.value) {
                viewModel.loadUserDiaries(userName)
            }
        } else {
            viewModel.searchTimeline(userName, query)
        }
    }

    CommonScreen(
        title = "타임라인",
        diaryList = diaryList,
        searchVisible = searchVisible,
        query = query,
        onQueryChange = onQueryChange,
        onSearchOpen = onSearchOpen,
        onSearchClose = onSearchClose,
        onMenuClick = onMenuClick,
        onFilterClick = { onFilterClick() },
        onViewDiary = onViewDiary,
        onEdit = onEdit,
        onDelete = onDelete
    )
}
