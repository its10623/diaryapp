package com.example.diaryapp.presentation.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.DiaryTitleStyle
import com.example.diaryapp.presentation.ui.theme.LabelTrackingStyle
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun TimelineCard(
    diaryId: Int,
    date: String = "",
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(top = 4.dp)
    ) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Border1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (date.isNotBlank()) {
                    Text(
                        text = date,
                        style = LabelTrackingStyle,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = title,
                    style = DiaryTitleStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = content,
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontSize = 13.sp,
                        color = TextSub2,
                        lineHeight = 20.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box {
                Text(
                    text = "⋯",
                    color = TextSub2,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onMoreClick
                        )
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = onDismiss,
                    containerColor = BackGround,
                    border = BorderStroke(1.dp, Border1)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "수정하기",
                                style = TextStyle(
                                    fontFamily = SansFamily,
                                    color = PrimaryAccent
                                )
                            )
                        },
                        onClick = {
                            onEdit(diaryId)
                            onDismiss()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "삭제하기",
                                style = TextStyle(
                                    fontFamily = SansFamily,
                                    color = PrimaryAccent
                                )
                            )
                        },
                        onClick = {
                            onDelete(diaryId)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}
