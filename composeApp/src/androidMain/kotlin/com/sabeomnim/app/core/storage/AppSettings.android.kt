package com.sabeomnim.app.core.storage

import android.content.Context
import android.content.SharedPreferences
import com.sabeomnim.app.core.designsystem.ThemeMode
import com.sabeomnim.app.core.i18n.AppLanguage

actual object AppSettings {
    private const val PREFS_NAME = "sabeomnim_prefs"
    private const val KEY_LANGUAGE = "app_language"
    private const val KEY_THEME = "app_theme"
    private const val KEY_VOICE_GENDER = "app_voice_gender"

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    actual fun getLanguage(): AppLanguage {
        val langStr = prefs?.getString(KEY_LANGUAGE, null) ?: return AppLanguage.DANISH
        return try {
            AppLanguage.valueOf(langStr)
        } catch (_: Exception) {
            AppLanguage.DANISH
        }
    }

    actual fun setLanguage(lang: AppLanguage) {
        prefs?.edit()?.putString(KEY_LANGUAGE, lang.name)?.apply()
    }

    actual fun getThemeMode(): ThemeMode {
        val themeStr = prefs?.getString(KEY_THEME, null) ?: return ThemeMode.SYSTEM
        return try {
            ThemeMode.valueOf(themeStr)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    }

    actual fun setThemeMode(mode: ThemeMode) {
        prefs?.edit()?.putString(KEY_THEME, mode.name)?.apply()
    }

    actual fun getVoiceGender(): VoiceGender {
        val genderStr = prefs?.getString(KEY_VOICE_GENDER, null) ?: return VoiceGender.FEMALE
        return try {
            VoiceGender.valueOf(genderStr)
        } catch (_: Exception) {
            VoiceGender.FEMALE
        }
    }

    actual fun setVoiceGender(gender: VoiceGender) {
        prefs?.edit()?.putString(KEY_VOICE_GENDER, gender.name)?.apply()
    }
}
