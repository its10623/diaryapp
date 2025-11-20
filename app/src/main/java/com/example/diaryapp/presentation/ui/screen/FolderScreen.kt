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