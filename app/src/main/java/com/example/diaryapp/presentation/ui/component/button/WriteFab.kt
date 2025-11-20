package com.example.diaryapp.presentation.ui.component.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.diaryapp.R
import com.example.diaryapp.presentation.ui.theme.BackGround
import com.example.diaryapp.presentation.ui.theme.PrimaryAccent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WriteFab(
    listState: LazyListState,
    onWrite: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var pressed by remember { mutableStateOf(false) }

    var fabVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            fabVisible = false
        } else {
            delay(600)
            fabVisible = true
        }
    }

    // 클릭 애니메이션
    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    AnimatedVisibility(
        visible = fabVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        FloatingActionButton(
            onClick = {
                pressed = true
                onWrite()

                scope.launch {
                    delay(150)
                    pressed = false
                }
            },
            shape = RoundedCornerShape(40.dp),
            containerColor = BackGround,
            modifier = Modifier
                .scale(scale)
                .size(60.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = PrimaryAccent,
                    shape = CircleShape,
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = "일기 작성",
                tint = PrimaryAccent,
                modifier = Modifier
                    .size(35.dp)
            )
        }
    }
}