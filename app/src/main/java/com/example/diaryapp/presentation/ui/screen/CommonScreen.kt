package com.example.diaryapp.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R
import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.presentation.ui.component.timeline.DateHeader
import com.example.diaryapp.presentation.ui.component.timeline.DiarySearchBar
import com.example.diaryapp.presentation.ui.component.timeline.TimelineIndicator
import com.example.diaryapp.presentation.ui.component.card.TimelineCard
import com.example.diaryapp.presentation.ui.component.timeline.TimelineEmptyView
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import java.time.Instant
import java.time.ZoneId

@Composable
fun CommonScreen(
    title: String,
    diaryList: List<Diary>,
    searchVisible: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchOpen: () -> Unit,
    onSearchClose: () -> Unit,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
    onViewDiary: (Int) -> Unit,
    onEdit: (Int) -> Unit,
    onDeleteRequest: (Int) -> Unit,
) {
    var selectedCardId by remember { mutableStateOf<Int?>(null) }
    var menuExpanded by remember { mutableStateOf(false) }

    val grouped = diaryList
        .groupBy {
            Instant.ofEpochMilli(it.createDate.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        .toList()

    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()

    Scaffold(
        containerColor = BackGround,
        topBar = {
            Box {
                AnimatedVisibility(
                    visible = searchVisible,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    DiarySearchBar(
                        value = query,
                        onValueChange = onQueryChange,
                        onClose = onSearchClose
                    )
                }
                AnimatedVisibility(
                    visible = !searchVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = { onMenuClick() }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_menu_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                                    contentDescription = null,
                                    tint = PrimaryAccent,
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            }

                            Text(
                                text = title,
                                style = LogoTextStyle.copy(fontSize = 35.sp),
                            )

                            IconButton(
                                onClick = onSearchOpen,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = null,
                                    tint = PrimaryAccent,
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            Modifier
                .background(BackGround)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            if (diaryList.isEmpty()) {
                TimelineEmptyView()
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    itemsIndexed(
                        items = grouped,
                        key = { _, (date, _) -> date }
                    ) { groupIndex, (date, diaries) ->
                        val isFirstGroup = groupIndex == 0
                        val isLastGroup = groupIndex == grouped.size - 1

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            TimelineIndicator(
                                isFirstGroup = isFirstGroup,
                                isLastGroup = isLastGroup
                            )

                            Column(modifier = Modifier.weight(1f)) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    DateHeader(date = date)

                                    if (groupIndex == 0) {
                                        IconButton(onClick = onFilterClick) {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_filter),
                                                contentDescription = null,
                                                tint = PrimaryAccent,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                                diaries.forEachIndexed { index, diary ->
                                    TimelineCard(
                                        diaryId = diary.id,
                                        modifier = Modifier.padding(
                                            bottom = if (index < diaries.size - 1) 8.dp else 16.dp
                                        ),
                                        title = diary.title,
                                        content = diary.content,
                                        onClick = { onViewDiary(diary.id) },
                                        menuExpanded = (selectedCardId == diary.id) && menuExpanded,
                                        onMoreClick = {
                                            selectedCardId = diary.id
                                            menuExpanded = true
                                        },
                                        onDismiss = { menuExpanded = false },
                                        onEdit = {
                                            menuExpanded = false
                                            onEdit(diary.id)
                                        },
                                        onDelete = {
                                            menuExpanded = false
                                            onDeleteRequest(diary.id)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}