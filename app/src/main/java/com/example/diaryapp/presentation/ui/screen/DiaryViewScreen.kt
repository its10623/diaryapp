package com.example.diaryapp.presentation.ui.screen

import android.R.id.bold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R
import com.example.diaryapp.domain.model.Diary
import com.example.diaryapp.presentation.ui.component.button.BackButton
import com.example.diaryapp.presentation.ui.component.button.Button
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Jua
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.TextHint
import com.example.diaryapp.presentation.ui.theme.TextHintStyle
import com.example.diaryapp.presentation.ui.theme.Typography

@Composable
fun DiaryViewScreen(
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onBack: () -> Unit = {},
    diaryId: Long? = null
) {
    val dummyList = listOf(
        Diary(id = 1, folder = "여행", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 11일"),
        Diary(id = 2, folder = "학교", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 10일"),
        Diary(id = 3, folder = "일상", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 09일"),
        Diary(id = 4, folder = "여행", name = "test", title =  "제목1", content =  "내용111...", date = "2025년 11월 09일"),
        Diary(id = 5, folder = "학교", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 08일"),
        Diary(id = 6, folder = "일상", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 10일"),
        Diary(id = 9, folder = "여행", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 09일"),
        Diary(id = 10, folder = "일상", name = "test", title =  "제목2", content =  "내용222", date = "2025년 11월 15일")
    )

    val diary = remember(diaryId) {
        dummyList.find { it.id == diaryId }
    }

    if (diary == null) {
        Text("일기를 찾을 수 없습니다")
        return
    }

    Scaffold(
        Modifier
            .background(BackGround)
            .fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                BackButton(
                    onBack,
                    Modifier.align(Alignment.TopStart)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "일기 상세보기",
                    style = LogoTextStyle.copy(fontSize = 40.sp),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        },
        bottomBar = {
            Column(

            ){
                HorizontalDivider(
                    thickness = 2.dp,
                    color = TextHint,
                    modifier = Modifier
                        .padding(start = 22.dp, end = 22.dp, top = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        text = "삭제",
                        icon = painterResource(R.drawable.ic_delete),
                        onClick = onDelete,
                        modifier = Modifier
                            .width(30.dp)
                            .padding(20.dp)
                    )

                    Button(
                        text = if (diary!!.isFavorite) "즐겨찾기 해제" else "즐겨찾기",
                        icon = painterResource(
                            if (diary!!.isFavorite) R.drawable.ic_star_filled
                            else R.drawable.ic_star_border
                        ),
                        onClick = {
                            viewModel.toggleFavoriteStatus(
                                diary!!.id,
                                !diary!!.isFavorite,
                                userName
                            )
                        },
                        modifier = Modifier
                            .width(30.dp)
                            .padding(20.dp)
                    )

                    Button(
                        text = "수정",
                        icon = painterResource(R.drawable.ic_edit_3),
                        onClick = onEdit,
                        modifier = Modifier
                            .width(30.dp)
                            .padding(20.dp)
                    )
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .background(BackGround)
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = diary.date,
                style = Typography.bodyLarge,
                color = PrimaryAccent,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp)
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = TextHint,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 16.dp)
            )

            Text(
                text = diary.title,
                fontSize = 20.sp,
                fontFamily = Jua,
                color = PrimaryAccent,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp)
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = TextHint,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)

            ){
                item {
                    Text(
                        text = diary.content,
                        fontSize = 16.sp,
                        fontFamily = Jua,
                        color = PrimaryAccent,
                        lineHeight = 26.sp,
                        modifier = Modifier
                            .padding(start = 22.dp, end = 22.dp),
                    )
                }

                item {

                }
            }
        }
    }
}
