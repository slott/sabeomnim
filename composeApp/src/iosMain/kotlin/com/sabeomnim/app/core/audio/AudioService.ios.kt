package com.sabeomnim.app.core.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sabeomnim.app.core.storage.AppSettings
import com.sabeomnim.app.core.storage.VoiceGender
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesisVoiceGender
import platform.AVFAudio.AVSpeechUtterance

class IosAudioService : AudioService {
    private val synthesizer = AVSpeechSynthesizer()
    private var currentGender: VoiceGender = AppSettings.getVoiceGender()

    private fun getKoreanVoice(gender: VoiceGender): AVSpeechSynthesisVoice? {
        val targetGender = if (gender == VoiceGender.MALE) {
            AVSpeechSynthesisVoiceGender.AVSpeechSynthesisVoiceGenderMale
        } else {
            AVSpeechSynthesisVoiceGender.AVSpeechSynthesisVoiceGenderFemale
        }
        val allVoices = AVSpeechSynthesisVoice.speechVoices()
        for (v in allVoices) {
            if (v is AVSpeechSynthesisVoice && v.language.startsWith("ko") && v.gender == targetGender) {
                return v
            }
        }
        return AVSpeechSynthesisVoice.voiceWithLanguage("ko-KR")
    }

    override fun setVoiceGender(gender: VoiceGender) {
        currentGender = gender
    }

    override fun speak(text: String, isSlow: Boolean) {
        currentGender = AppSettings.getVoiceGender()
        val utterance = AVSpeechUtterance.speechUtteranceWithString(text)
        utterance.voice = getKoreanVoice(currentGender)
        utterance.pitchMultiplier = if (currentGender == VoiceGender.MALE) 0.88f else 1.05f
        utterance.rate = if (isSlow) 0.35f else 0.5f // iOS speech rate defaults around 0.5
        synthesizer.speakUtterance(utterance)
    }

    override fun stop() {
        synthesizer.stopSpeakingAtBoundary(platform.AVFAudio.AVSpeechBoundary.AVSpeechBoundaryImmediate)
    }
}

@Composable
actual fun rememberAudioService(): AudioService {
    return remember { IosAudioService() }
}
