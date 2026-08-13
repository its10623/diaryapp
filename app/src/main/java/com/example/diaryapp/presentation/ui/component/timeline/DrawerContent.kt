package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.ButtonText
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun DrawerContent(
    folders: List<String>,
    onClose: () -> Unit,
    onWriteDiary: () -> Unit,
    onFolderClick: (String) -> Unit = {},
    onAddFolder: () -> Unit = {},
    onTrashed: () -> Unit = {},
    onSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
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
            // 즐겨찾기 pill - black filled
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(PrimaryAccent)
                        .clickable { onFolderClick("즐겨찾기") }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "즐겨찾기",
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = ButtonText
                        )
                    )
                }
            }

            // Folder items (text only, no icons)
            folders.forEach { folder ->
                item {
                    Text(
                        text = folder,
                        style = TextStyle(
                            fontFamily = SansFamily,
                            fontSize = 15.sp,
                            color = PrimaryAccent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .clickable { onFolderClick(folder) }
                    )
                }
            }

            // Divider
            item {
                HorizontalDivider(
                    color = Border1,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Settings items
            item {
                Text(
                    text = "설정",
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = TextSub2
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
                        color = TextSub2
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
                        color = TextSub2
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clickable { onLogout() }
                )
            }
        }

        // Bottom buttons (fixed at bottom)
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
