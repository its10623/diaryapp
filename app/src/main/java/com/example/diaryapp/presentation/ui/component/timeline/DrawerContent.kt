package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.TextHint

@Composable
fun DrawerContent(
    folders: List<String>,
    onFolderClick: (String) -> Unit = {},
    onAddFolder: () -> Unit = {},
    onTrashed: () -> Unit = {},
    onSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {//TODO 길게 터치하면 폴더명 수정, 폴더 삭제 (다이얼로그 포함)
        item {
            Text(
                text = "폴더 목록",
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 22.sp,
                color = PrimaryAccent
            )
        }
        //
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .combinedClickable(
                        onClick = {
                            onFolderClick("즐겨찾기")
                        },
                        onLongClick = {

                        }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star_border),
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "즐겨찾기",
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 18.sp,
                        color = PrimaryAccent
                    )
                }
            }
        }
        folders.forEach { folder ->
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(64.dp))
                        .clickable { onFolderClick(folder) },
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_folder),
                            contentDescription = null,
                            tint = PrimaryAccent,
                            modifier = Modifier.size(26.dp)
                        )
                        Text(
                            text = folder,
                            modifier = Modifier
                                .padding(12.dp),
                            fontSize = 18.sp,
                            color = PrimaryAccent
                        )
                    }
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .clickable { onAddFolder() },
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_folder_plus),
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "폴더 추가",
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 18.sp,
                        color = PrimaryAccent
                    )
                }
            }
        }
        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                thickness = 2.dp,
                color = TextHint
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .clickable { onTrashed() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 20.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "휴지통",
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 18.sp,
                        color = PrimaryAccent
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .clickable { onSettings() },
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_settings_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "설정",
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 18.sp,
                        color = PrimaryAccent
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .clickable { onLogout() },
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_logout),
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "로그아웃",
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 18.sp,
                        color = PrimaryAccent
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
}