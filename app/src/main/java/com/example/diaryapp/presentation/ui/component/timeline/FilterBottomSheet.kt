package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sortType: SortType,
    selectedDates: Set<LocalDate>,
    diaryDates: List<LocalDate>,
    onApply: (SortType, Set<LocalDate>) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    var tempSort by remember { mutableStateOf(sortType) }
    var tempDates by remember { mutableStateOf(selectedDates.toMutableSet()) }

// 딱 1회만 초기화
    LaunchedEffect(sortType, selectedDates) {
        tempSort = sortType
        tempDates = selectedDates.toMutableSet()
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BackGround,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Border1)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text("필터", fontSize = 22.sp)

            Spacer(Modifier.height(30.dp))

            // 정렬 옵션
            Text("정렬", fontSize = 18.sp)

            FilterOptionItem(
                text = "최신순",
                selected = tempSort == SortType.RECENT,
                onClick = { tempSort = SortType.RECENT }
            )

            FilterOptionItem(
                text = "오래된순",
                selected = tempSort == SortType.OLD,
                onClick = { tempSort = SortType.OLD }
            )

            Spacer(Modifier.height(16.dp))

            // 날짜 다중 선택
            Text("특정 날짜 선택", fontSize = 18.sp)

            val distinctDates = diaryDates.distinct().sortedDescending()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(distinctDates) { date ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                tempDates = tempDates.toMutableSet().apply {
                                    if (contains(date)) remove(date) else add(date)
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tempDates.contains(date),
                            onCheckedChange = {
                                tempDates = tempDates.toMutableSet().apply {
                                    if (contains(date)) remove(date) else add(date)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = PrimaryAccent
                            )
                        )
                        Text(text = date.toString(), fontSize = 16.sp)
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        tempSort = SortType.NONE
                        tempDates = emptySet<LocalDate>().toMutableSet()
                        onReset()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryAccent,
                        contentColor = BackGround
                    )
                ) {

                    Text("초기화")
                }

                Button(
                    onClick = {
                        onApply(tempSort, tempDates)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryAccent,
                        contentColor = BackGround
                    )
                ) {
                    Text("적용하기")
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun FilterOptionItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected, onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = PrimaryAccent

            )
        )
        Text(text, fontSize = 16.sp)
    }
}

enum class SortType {
    RECENT,
    OLD,
    NONE
}