package com.example.diaryapp.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent

@Composable
fun TimelineIndicator(
    modifier: Modifier = Modifier,
    color: Color = PrimaryAccent,
    isFirstGroup: Boolean,
    isLastGroup: Boolean
) {
    val dotRadius = 6.dp
    val dotY = 20.dp
    val lineBottomPadding = 16.dp
    val lineWidth = 2.dp

    val density = LocalDensity.current
    val dotRadiusPx = with(density) { dotRadius.toPx() }
    val dotYPx = with(density) { dotY.toPx() }
    val lineBottomPaddingPx = with(density) { lineBottomPadding.toPx() }
    val lineWidthPx = with(density) { lineWidth.toPx() }

    Spacer(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 18.dp)
            .width(20.dp)
            .drawBehind {
                drawCircle(
                    color = color,
                    radius = dotRadiusPx,
                    center = Offset(center.x, dotYPx)
                )

                // 선의 시작점 (첫번째 그룹이면 점의 위치에서 시작, 아니면 맨 위에서 시작)
                val lineTop = if (isFirstGroup) dotYPx else 0f
                // 선의 끝점 (마지막 그룹이면 하단 여백을 제외한 위치, 아니면 맨 아래까지)
                val lineBottom = if (isLastGroup) size.height - lineBottomPaddingPx else size.height

                drawLine(
                    color = color,
                    start = Offset(center.x, lineTop),
                    end = Offset(center.x, lineBottom),
                    strokeWidth = lineWidthPx
                )
            }
    )
}