package com.sabeomnim.app.core.audio

import androidx.compose.runtime.Composable
import com.sabeomnim.app.core.storage.VoiceGender

interface AudioService {
    fun speak(text: String, isSlow: Boolean = false)
    fun setVoiceGender(gender: VoiceGender)
    fun stop()
}

@Composable
expect fun rememberAudioService(): AudioService
