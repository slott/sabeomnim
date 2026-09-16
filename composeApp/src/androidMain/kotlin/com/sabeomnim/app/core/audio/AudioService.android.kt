package com.sabeomnim.app.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sabeomnim.app.core.storage.AppSettings
import com.sabeomnim.app.core.storage.VoiceGender
import java.util.Locale

class AndroidAudioService(context: Context) : AudioService {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var currentGender: VoiceGender = AppSettings.getVoiceGender()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.KOREAN)
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    isInitialized = true
                    applyVoiceForGender(currentGender)
                }
            }
        }
    }

    override fun setVoiceGender(gender: VoiceGender) {
        currentGender = gender
        if (isInitialized) {
            applyVoiceForGender(gender)
        }
    }

    private fun applyVoiceForGender(gender: VoiceGender) {
        val ttsInstance = tts ?: return
        val koVoices = ttsInstance.voices?.filter {
            it.locale.language == "ko" || it.locale.toString().startsWith("ko")
        } ?: emptyList()

        if (gender == VoiceGender.MALE) {
            // Find male voice:
            // Google TTS uses "kob" (Voice B) or "koc" (Voice C) for male.
            // Other engines may contain "male" or "#male".
            val maleVoice = koVoices.firstOrNull { v ->
                val name = v.name.lowercase()
                (name.contains("kob") || name.contains("koc") || name.contains("male")) && !name.contains("female")
            } ?: koVoices.firstOrNull { v ->
                val name = v.name.lowercase()
                name.contains("kob") || name.contains("koc") || name.contains("male")
            }

            if (maleVoice != null) {
                try {
                    ttsInstance.voice = maleVoice
                } catch (_: Exception) {}
            }
            ttsInstance.setPitch(0.85f)
        } else {
            // Female voice:
            // Google TTS uses "ism" (Voice A) or "kod" (Voice D) for female.
            val femaleVoice = koVoices.firstOrNull { v ->
                val name = v.name.lowercase()
                (name.contains("ism") || name.contains("kod") || name.contains("female"))
            } ?: koVoices.firstOrNull()

            if (femaleVoice != null) {
                try {
                    ttsInstance.voice = femaleVoice
                } catch (_: Exception) {}
            }
            ttsInstance.setPitch(1.05f)
        }
    }

    override fun speak(text: String, isSlow: Boolean) {
        if (!isInitialized) return
        val preferredGender = AppSettings.getVoiceGender()
        if (preferredGender != currentGender) {
            setVoiceGender(preferredGender)
        } else {
            applyVoiceForGender(currentGender)
        }
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
