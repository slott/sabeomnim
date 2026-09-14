package com.sabeomnim.app.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class AndroidAudioService(context: Context) : AudioService {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.KOREAN)
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    isInitialized = true
                }
            }
        }
    }

    override fun speak(text: String, isSlow: Boolean) {
        if (!isInitialized) return
        tts?.setSpeechRate(if (isSlow) 0.65f else 0.95f)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    }

    override fun stop() {
        tts?.stop()
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}

@Composable
actual fun rememberAudioService(): AudioService {
    val context = LocalContext.current
    val service = remember { AndroidAudioService(context) }
    DisposableEffect(service) {
        onDispose {
            service.release()
        }
    }
    return service
}
