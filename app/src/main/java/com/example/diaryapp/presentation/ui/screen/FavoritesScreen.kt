package com.example.diaryapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.diaryapp.presentation.ui.component.timeline.SortType
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel

@Composable
fun FavoritesScreen(
    userName: String,
    viewModel: DiaryViewModel,
    onMenuClick: () -> Unit,
    onViewDiary: (Int) -> Unit,
    onFilterClick: () -> Unit,
    onEdit: (Int) -> Unit,
    onDeleteRequest: (Int) -> Unit
) {
    val diaries by viewModel.filteredDiaryList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.applyFilter(SortType.RECENT, emptySet())
        viewModel.loadFavoriteDiaries(userName)
    }

    CommonScreen(
        title = "즐겨찾기",
        diaryList = diaries,
        searchVisible = searchVisible,
        query = query,
        onQueryChange = { query = it },
        onSearchOpen = { searchVisible = true },
        onSearchClose = {
            searchVisible = false
            query = ""
            viewModel.loadFavoriteDiaries(userName)
        },
        onMenuClick = onMenuClick,
        onFilterClick = { onFilterClick() },
        onViewDiary = onViewDiary,
        onEdit = onEdit,
        onDeleteRequest = onDeleteRequest
    )
}
