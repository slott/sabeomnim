package com.sabeomnim.app.core.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechUtterance

class IosAudioService : AudioService {
    private val synthesizer = AVSpeechSynthesizer()
    private val koreanVoice = AVSpeechSynthesisVoice.voiceWithLanguage("ko-KR")

    override fun speak(text: String, isSlow: Boolean) {
        val utterance = AVSpeechUtterance.speechUtteranceWithString(text)
        utterance.voice = koreanVoice
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
