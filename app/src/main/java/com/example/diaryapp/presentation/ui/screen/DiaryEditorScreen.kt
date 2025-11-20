package com.example.diaryapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.EditorMode
import com.example.diaryapp.presentation.ui.component.button.BackButton
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.LogoTextStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.inter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DiaryEditorScreen(
    mode: EditorMode,
    initialTitle: String = "",
    initialContent: String = "",
    initialDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
    onBack: () -> Unit = {},
    onSave: (String, String) -> Unit = {_, _ -> },
) {
    var title by remember { mutableStateOf(initialTitle) }
    var content by remember { mutableStateOf(initialContent) }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BackButton(
                    onBack,
                    Modifier.align(Alignment.TopStart)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (mode == EditorMode.CREATE)"일기 작성" else "일기 수정",
                    style = LogoTextStyle.copy(fontSize = 40.sp),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(BackGround)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("날짜", color = PrimaryAccent,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = initialDate,
                    onValueChange = {},
                    textStyle = TextStyle(
                        color = PrimaryAccent,
                    ),
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text("제목", color = PrimaryAccent,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,           // 한 줄 입력 처리용
                    textStyle = TextStyle(
                        fontFamily = inter,
                        color = PrimaryAccent,
                        fontSize = 16.sp
                    ),
                    placeholder = {
                        Text(
                            text = "제목을 입력하세요",
                            color = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text("내용", color = PrimaryAccent,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp),
                    placeholder = {
                        Text(
                            text = "내용을 입력하세요",
                            color = Color.Gray
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = inter,
                        color = PrimaryAccent,
                        fontSize = 16.sp
                    ),
                    maxLines = Int.MAX_VALUE,
                    shape = RoundedCornerShape(18.dp),
                )

                Spacer(Modifier.height(24.dp))

                LoginButton(
                    text = if (mode == EditorMode.CREATE) "저장" else "수정 완료",
                    onClick = { onSave(title, content) },
                    enabled = title.isNotBlank()
                            && content.isNotBlank()
                )
            }
        }
    }
}
