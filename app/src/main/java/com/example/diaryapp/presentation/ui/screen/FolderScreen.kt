package com.example.diaryapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.example.diaryapp.domain.model.Diary

@Composable
fun FolderScreen(
    folderName: String,
    diaryList: List<Diary>,
    searchVisible: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchOpen: () -> Unit,
    onSearchClose: () -> Unit,
    onMenuClick: () -> Unit,
    onViewDiary: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onFilterClick: () -> Unit = {}

) {
    val diaries by viewModel.filteredFolderList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(folderName, query) {
        if (query.isBlank()) {
            viewModel.loadFolderDiaries(userName, folderName)
        } else {
            viewModel.searchInFolder(userName, folderName, query)
        }
    }

    CommonScreen(
        title = folderName,
        diaryList = diaryList,
        searchVisible = searchVisible,
        query = query,
        onQueryChange = onQueryChange,
        onSearchOpen = onSearchOpen,
        onSearchClose = onSearchClose,
        onMenuClick = onMenuClick,
        onFilterClick = onFilterClick,
        onViewDiary = onViewDiary,
        onEdit = onEdit,
        onDelete = onDelete
    )
}