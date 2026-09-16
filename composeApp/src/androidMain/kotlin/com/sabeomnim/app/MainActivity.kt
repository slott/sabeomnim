package com.sabeomnim.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.sabeomnim.app.core.storage.AppSettings
import com.sabeomnim.app.core.logging.CrashlyticsLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettings.init(applicationContext)
        CrashlyticsLogger.log("Sabeomnim MainActivity initialized")
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}
