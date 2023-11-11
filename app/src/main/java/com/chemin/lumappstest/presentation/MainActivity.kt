package com.chemin.lumappstest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chemin.lumappstest.getAppComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.chemin.lumappstest.design.theme.LumappsTestTheme {
                LumappsTestComposeApp(
                    appComponent = application.getAppComponent(),
                )
            }
        }
    }
}
