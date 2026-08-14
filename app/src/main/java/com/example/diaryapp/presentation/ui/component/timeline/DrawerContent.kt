package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.component.button.DropDown
import com.example.diaryapp.presentation.ui.component.button.DropDownItem
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.ButtonText
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun DrawerContent(
    folders: List<String>,
    selectedFolder: String = "",
    onClose: () -> Unit,
    onWriteDiary: () -> Unit,
    onFolderClick: (String) -> Unit,
    onAddFolder: () -> Unit,
    onTrashed: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit,
    onEditFolderName: (String, String) -> Unit,
    onDeleteFolder: (String) -> Unit
) {
    var expandedFolder by remember { mutableStateOf<String?>(null) }
    var folderToDelete by remember { mutableStateOf<String?>(null) }
    var folderToEdit by remember { mutableStateOf<String?>(null) }

    folderToDelete?.let { folder ->
        Dialog(
            title = "$folder 폴더를 삭제하시겠습니까?",
            isTextField = false,
            onDismiss = { folderToDelete = null },
            onConfirm = {
                onDeleteFolder(folder)
                folderToDelete = null
            }
        )
    }

    folderToEdit?.let { folder ->
        Dialog(
            title = "폴더명 수정",
            confirmText = "수정",
            onDismiss = { folderToEdit = null },
            onConfirm = { newFolder ->
                if (newFolder.isNotBlank()) {
                    onEditFolderName(folder, newFolder)
                }
                folderToEdit = null
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(BackGround)
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
                text = "폴더",
                style = TextStyle(
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = PrimaryAccent
                )
            )
            Text(
                text = "×",
                style = TextStyle(
                    fontFamily = SansFamily,
                    fontSize = 20.sp,
                    color = TextSub2
                ),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClose
                )
            )
        }

        HorizontalDivider(color = Border1)

        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                val isFavSelected = selectedFolder == "즐겨찾기"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isFavSelected) PrimaryAccent else Color.Transparent)
                        .clickable { onFolderClick("즐겨찾기") }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "즐겨찾기",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontWeight = if (isFavSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp,
                            color = if (isFavSelected) ButtonText else PrimaryAccent
                        )
                    )
                }
            }

            folders.forEach { folder ->
                item {
                    val isSelected = selectedFolder == folder
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(if (isSelected) PrimaryAccent else Color.Transparent)
                                .combinedClickable(
                                    onClick = { onFolderClick(folder) },
                                    onLongClick = { expandedFolder = folder }
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = folder,
                                style = TextStyle(
                                    fontFamily = SansFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = if (isSelected) ButtonText else PrimaryAccent
                                )
                            )
                        }
                        DropDown(
                            menuExpanded = expandedFolder == folder,
                            onDismiss = { expandedFolder = null },
                            items = listOf(
                                DropDownItem("수정하기") {
                                    expandedFolder = null
                                    folderToEdit = folder
                                },
                                DropDownItem("삭제하기") {
                                    expandedFolder = null
                                    folderToDelete = folder
                                }
                            )
                        )
                    }
                }
            }

            item {
                HorizontalDivider(
                    color = Border1,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                Text(
                    text = "설정",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = PrimaryAccent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clickable { onSettings() }
                )
            }
            item {
                Text(
                    text = "휴지통",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = PrimaryAccent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clickable { onTrashed() }
                )
            }
            item {
                Text(
                    text = "로그아웃",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = PrimaryAccent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clickable { onLogout() }
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onAddFolder,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, PrimaryAccent),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = PrimaryAccent
                )
            ) {
                Text(
                    text = "+ 폴더 생성",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }

            Button(
                onClick = onWriteDiary,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAccent,
                    contentColor = ButtonText
                )
            ) {
                Text(
                    text = "일기 작성",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
