package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.SearchDrag
import com.example.diaryapp.presentation.ui.theme.TextSub2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        // 엔터누르면 키보드 다운
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

        leadingIcon = {
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = PrimaryAccent,
                    modifier = Modifier
                        .size(35.dp)
                )
            }
        },

        placeholder = {
            if (value.isEmpty()) {
                Text("검색할 키워드를 입력해주세요", color = TextSub2)
            }
        },

        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = PrimaryAccent,
                modifier = Modifier
                    .size(35.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester),        // 스크린에서 검색창 클릭시 키보드 호출

        singleLine = true,
        textStyle = TextStyle(
            fontFamily = SansFamily,
            fontSize = 15.sp,
            color = PrimaryAccent
        ),
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchDrag,
            unfocusedContainerColor = SearchDrag,
            cursorColor = PrimaryAccent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}
