package com.example.diaryapp.presentation.ui.component

import android.widget.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.BoundaryLine
import com.example.diaryapp.presentation.ui.theme.ErrorColor
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.TextHintStyle

@Composable
fun Dialog(
    title: String,
    label: String = "",
    confirmText: String = "확인",
    dismissText: String = "취소",
    isTextField: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {


        Surface(
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 2.dp,
            color = BackGround,
            modifier = Modifier.width(300.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 30.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (isTextField) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = {
                            Text(
                                text = label,
                                style = TextHintStyle
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = PrimaryAccent,
                            unfocusedIndicatorColor = BoundaryLine,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = ErrorColor
                        )
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PrimaryAccent
                        ),
                        onClick = onDismiss
                    ) { Text(dismissText, fontSize = 16.sp) }

                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PrimaryAccent,
                        ),
                        onClick = { onConfirm(input) }
                    ) { Text(confirmText, fontSize = 16.sp) }
                }
            }
        }
    }
}