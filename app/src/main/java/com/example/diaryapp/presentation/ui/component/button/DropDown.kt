package com.example.diaryapp.presentation.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.Border1
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily

data class DropDownItem(
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun DropDown(
    menuExpanded: Boolean,
    onDismiss: () -> Unit,
    items: List<DropDownItem>
) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = onDismiss,
        containerColor = BackGround,
        border = BorderStroke(1.dp, Border1),
        shape = RoundedCornerShape(12.dp)
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = item.label,
                        style = TextStyle(
                            fontFamily = SansFamily,
                            color = PrimaryAccent
                        )
                    )
                },
                onClick = {
                    item.onClick()
                    onDismiss()
                }
            )
        }
    }
}
