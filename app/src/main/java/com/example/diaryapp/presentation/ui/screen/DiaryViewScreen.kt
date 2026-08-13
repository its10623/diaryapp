package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.DiaryContentStyle
import com.example.diaryapp.presentation.ui.theme.DiaryTitleStyle
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.LabelTrackingStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub1
import com.example.diaryapp.presentation.ui.theme.TextSub2
import com.example.diaryapp.presentation.ui.theme.TextSub3
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun DiaryViewScreen(
    id: Int,
    userName: String,
    viewModel: DiaryViewModel,
    onEdit: (Int) -> Unit = {},
    onDeleteRequest: (Int) -> Unit,
    onBack: () -> Unit = {},
) {
    val diary by viewModel.selectedDiary.collectAsState()

    LaunchedEffect(id) {
        if (id != 0) {
            viewModel.loadDiary(id)
        }
    }

    if (diary == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PrimaryAccent)
        }
        return
    }

    val dateText = Instant.ofEpochMilli(diary!!.createDate.time)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .let { date ->
            val dayAbbr = date.dayOfWeek.name.take(3)
            "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 · $dayAbbr"
        }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        containerColor = BackGround
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontSize = 20.sp,
                        color = PrimaryAccent
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onBack() }
                )
                Text(
                    text = "⋯",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontSize = 20.sp,
                        color = TextSub2
                    )
                )
            }

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                // Date tracking label
                Text(text = dateText, style = LabelTrackingStyle)

                Spacer(Modifier.height(16.dp))

                // Title (large serif italic)
                Text(
                    text = diary!!.title,
                    style = DiaryTitleStyle.copy(fontSize = 26.sp),
                )

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Border1, thickness = 0.5.dp)
                Spacer(Modifier.height(20.dp))

                // Body content (serif)
                Text(
                    text = diary!!.content,
                    style = DiaryContentStyle.copy(lineHeight = 28.sp),
                )

                Spacer(Modifier.height(40.dp))
            }

            // Bottom bar with text link actions
            Column {
                HorizontalDivider(color = Border1)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Favorite toggle
                    Text(
                        text = if (diary!!.isFavorite) "★ 즐겨찾기 해제" else "☆ 즐겨찾기",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontSize = 13.sp,
                            color = TextSub2
                        ),
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            viewModel.toggleFavoriteStatus(diary!!.id, !diary!!.isFavorite, userName)
                        }
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "수정",
                            style = TextStyle(
                                fontFamily = SansFamily,
                                fontSize = 13.sp,
                                color = TextSub1
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onEdit(id) }
                        )
                        Text(
                            text = "  |  ",
                            style = TextStyle(
                                fontFamily = SansFamily,
                                fontSize = 13.sp,
                                color = TextSub3
                            )
                        )
                        Text(
                            text = "삭제",
                            style = TextStyle(
                                fontFamily = SansFamily,
                                fontSize = 13.sp,
                                color = ErrorColor
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onDeleteRequest(diary!!.id) }
                        )
                    }
                }
            }
        }
    }
}
