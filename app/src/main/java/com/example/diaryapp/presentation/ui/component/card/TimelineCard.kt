package com.example.diaryapp.presentation.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.diaryapp.R
import com.example.diaryapp.presentation.ui.component.button.MoreButton
import com.example.diaryapp.presentation.ui.theme.BoundaryLine
import com.example.diaryapp.presentation.ui.theme.Card
import com.example.diaryapp.presentation.ui.theme.DiaryContentStyle
import com.example.diaryapp.presentation.ui.theme.DiaryTitleStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun TimelineCard(
    diaryId: Int,
    title: String,
    content: String,
    onClick: () -> Unit,
    menuExpanded: Boolean,
    onMoreClick: () -> Unit,
    onDismiss: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, BoundaryLine),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true, // 카드 안에만 ripple
                    radius = 140.dp
                ),
                onClick = onClick
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = title,
                    style = DiaryTitleStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                    )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    style = DiaryContentStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier
                    .combinedClickable(onClick = onClick),

                ) {
                MoreButton(
                    onClick = onMoreClick
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = onDismiss,
                    modifier = Modifier
                        .background(Card, RoundedCornerShape(16.dp))
                        .align(Alignment.Center),
                    border = BorderStroke(1.dp, BoundaryLine),
                    shape = RoundedCornerShape(10.dp),

                    shadowElevation = 4.dp,
                    containerColor = Card

                ) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_edit_3),
                                contentDescription = null,
                                tint = PrimaryAccent
                            )
                        },
                        text = { Text("수정하기", color = PrimaryAccent) },
                        onClick = {
                            onEdit(diaryId)
                        }
                    )
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = null,
                                tint = PrimaryAccent
                            )
                        },
                        text = { Text("삭제하기", color = PrimaryAccent) },
                        onClick = {
                            onDelete(diaryId)
                        }
                    )
                }
            }
        }
    }
}