package com.example.diaryapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.diaryapp.presentation.ui.screen.SplashScreen
import com.example.diaryapp.presentation.ui.theme.DiaryAppTheme


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            DiaryAppTheme {
                SplashScreen(onSplashFinished = {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                })
            }
        }
    }
}

