package com.sabeomnim.app.core.storage

import com.sabeomnim.app.core.designsystem.ThemeMode
import com.sabeomnim.app.core.i18n.AppLanguage

enum class VoiceGender {
    FEMALE,
    MALE
}

expect object AppSettings {
    fun getLanguage(): AppLanguage
    fun setLanguage(lang: AppLanguage)
    fun getThemeMode(): ThemeMode
    fun setThemeMode(mode: ThemeMode)
    fun getVoiceGender(): VoiceGender
    fun setVoiceGender(gender: VoiceGender)
}
