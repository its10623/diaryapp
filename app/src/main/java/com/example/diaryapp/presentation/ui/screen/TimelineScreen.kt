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
    CommonScreen(
        title = "타임라인",
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
