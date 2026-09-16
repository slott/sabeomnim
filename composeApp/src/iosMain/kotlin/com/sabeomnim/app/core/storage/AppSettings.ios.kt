package com.sabeomnim.app.core.storage

import com.sabeomnim.app.core.designsystem.ThemeMode
import com.sabeomnim.app.core.i18n.AppLanguage
import platform.Foundation.NSUserDefaults

actual object AppSettings {
    private const val KEY_LANGUAGE = "app_language"
    private const val KEY_THEME = "app_theme"
    private const val KEY_VOICE_GENDER = "app_voice_gender"

    private val defaults: NSUserDefaults
        get() = NSUserDefaults.standardUserDefaults

    actual fun getLanguage(): AppLanguage {
        val langStr = defaults.stringForKey(KEY_LANGUAGE) ?: return AppLanguage.DANISH
        return try {
            AppLanguage.valueOf(langStr)
        } catch (_: Exception) {
            AppLanguage.DANISH
        }
    }

    actual fun setLanguage(lang: AppLanguage) {
        defaults.setObject(lang.name, forKey = KEY_LANGUAGE)
    }

    actual fun getThemeMode(): ThemeMode {
        val themeStr = defaults.stringForKey(KEY_THEME) ?: return ThemeMode.SYSTEM
        return try {
            ThemeMode.valueOf(themeStr)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    }

    actual fun setThemeMode(mode: ThemeMode) {
        defaults.setObject(mode.name, forKey = KEY_THEME)
    }

    actual fun getVoiceGender(): VoiceGender {
        val genderStr = defaults.stringForKey(KEY_VOICE_GENDER) ?: return VoiceGender.FEMALE
        return try {
            VoiceGender.valueOf(genderStr)
        } catch (_: Exception) {
            VoiceGender.FEMALE
        }
    }

    actual fun setVoiceGender(gender: VoiceGender) {
        defaults.setObject(gender.name, forKey = KEY_VOICE_GENDER)
    }
}
