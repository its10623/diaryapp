package com.example.diaryapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel

@Composable
fun FolderScreen(
    userName: String,
    folderName: String,
    viewModel: DiaryViewModel,
    onMenuClick: () -> Unit,
    onViewDiary: (Int) -> Unit,
    onEdit: (Int) -> Unit,
    onFilterClick: () -> Unit = {},
    onDeleteRequest: (Int) -> Unit
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
        diaryList = diaries,
        searchVisible = searchVisible,
        query = query,
        onQueryChange = { query = it },
        onSearchOpen = { searchVisible = true },
        onSearchClose = {
            searchVisible = false
            query = ""
            viewModel.loadFolderDiaries(userName, folderName)
        },
        onMenuClick = onMenuClick,
        onFilterClick = onFilterClick,
        onViewDiary = onViewDiary,
        onEdit = onEdit,
        onDeleteRequest = onDeleteRequest
    )
}