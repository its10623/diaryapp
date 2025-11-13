package com.example.diaryapp.presentation.ui.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.diaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 8.dp)
    ) {
        IconButton(
            onClick = { /*onBackClick()*/ }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_left),
                modifier = Modifier
                    .size(35.dp),
                contentDescription = "뒤로가기"
            )
        }
    }
}