package com.example.diaryapp.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.timeline.EditorMode
import com.example.diaryapp.presentation.ui.component.button.LoginButton
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.DiaryContentStyle
import com.example.diaryapp.presentation.ui.theme.DiaryTitleStyle
import com.example.diaryapp.presentation.ui.theme.LabelTrackingStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub1
import com.example.diaryapp.presentation.ui.theme.TextSub2
import com.example.diaryapp.presentation.ui.theme.TextSub3
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
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGround
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
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(16.dp))

                // Header row: back + title
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = if (mode == EditorMode.CREATE) "일기 작성" else "일기 수정",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = PrimaryAccent
                        )
                    )
                    Spacer(Modifier.weight(1f))
                }

                Spacer(Modifier.height(24.dp))

                // Date row with serif italic
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = date,
                        style = DiaryContentStyle.copy(
                            fontStyle = FontStyle.Italic,
                            color = TextSub1
                        )
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "▾",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontSize = 13.sp,
                            color = TextSub2
                        )
                    )
                }

                HorizontalDivider(
                    color = Border1,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Title section
                Text(text = "제목", style = LabelTrackingStyle)
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = DiaryTitleStyle.copy(fontSize = 20.sp),
                    placeholder = {
                        Text(
                            "일기 제목을 입력하세요",
                            style = DiaryTitleStyle.copy(fontSize = 20.sp, color = TextSub3)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = PrimaryAccent,
                        unfocusedIndicatorColor = Border1,
                        cursorColor = PrimaryAccent,
                    )
                )

                HorizontalDivider(
                    color = Border1,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Content section
                Text(text = "내용", style = LabelTrackingStyle)
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 320.dp),
                    textStyle = DiaryContentStyle,
                    placeholder = {
                        Text(
                            "오늘의 이야기를 적어보세요",
                            style = DiaryContentStyle.copy(color = TextSub3)
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = PrimaryAccent,
                    )
                )

                Spacer(Modifier.height(32.dp))

                // Save button
                LoginButton(
                    text = if (mode == EditorMode.CREATE) "저  장" else "수정 완료",
                    onClick = {
                        if (mode == EditorMode.CREATE) {
                            viewModel.writeDiary(
                                userName = userName,
                                folder = folderName,
                                title = title,
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
                    enabled = title.isNotBlank() && content.isNotBlank()
                )

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
