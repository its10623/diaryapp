package com.example.diaryapp.presentation.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.diaryapp.presentation.viewmodel.DiaryViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun DiaryEditorScreen(
    mode: EditorMode,
    id: Int?,
    userName: String,
    folderName: String?,
    viewModel: DiaryViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val diary by viewModel.selectedDiary.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.writeSuccess.collectLatest { success ->
            if (success) {
                onSave()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updateSuccess.collectLatest { success ->
            if (success) {
                onSave()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
        }
    }

    LaunchedEffect(id) {
        if (mode == EditorMode.EDIT && id != null && id != 0) {
            viewModel.loadDiary(id)
        }
    }

    LaunchedEffect(diary) {
        diary?.let {
            title = it.title
            content = it.content
            date = Instant.ofEpochMilli(it.createDate.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd (E)"))
        }
    }

    LaunchedEffect(Unit) {
        if (mode == EditorMode.CREATE) {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd (E)"))
        }
    }

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
                    value = date,
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
                    singleLine = true,
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
                    onClick = {
                        if (mode == EditorMode.CREATE) {
                            viewModel.writeDiary(
                                userName = userName,
                                folder =  folderName,
                                title =  title,
                                content = content
                            )
                        } else {
                            diary?.let { old ->
                                viewModel.updateDiary(
                                    old.copy(
                                        title = title,
                                        content = content,
                                        updateDate = Date()
                                    )
                                )
                            }
                        }
                    },
                    enabled = title.isNotBlank()
                            && content.isNotBlank()
                )
            }
        }
    }
}
