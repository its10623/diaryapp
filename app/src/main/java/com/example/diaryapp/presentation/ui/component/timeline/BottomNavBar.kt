package com.example.diaryapp.presentation.ui.component.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.presentation.ui.screen.bottomNavScreen
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import com.example.diaryapp.presentation.ui.theme.SansFamily
import com.example.diaryapp.presentation.ui.theme.TextSub2

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = BackGround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavScreen.forEach { screen ->
                val selected = currentRoute == screen.bottomRoute
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    text = screen.title,
                    style = TextStyle(
                        fontFamily = SansFamily,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 15.sp,
                        color = if (selected) PrimaryAccent else TextSub2
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onNavigate(screen.bottomRoute)
                        }
                )
            }
        }
    }
}
