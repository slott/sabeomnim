package com.sabeomnim.app.core.audio

import androidx.compose.runtime.Composable

interface AudioService {
    fun speak(text: String, isSlow: Boolean = false)
    fun stop()
}

@Composable
expect fun rememberAudioService(): AudioService
