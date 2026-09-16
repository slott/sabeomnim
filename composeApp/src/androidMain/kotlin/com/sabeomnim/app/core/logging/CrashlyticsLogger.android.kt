package com.sabeomnim.app.core.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics

actual object CrashlyticsLogger {
    actual fun log(message: String) {
        runCatching {
            FirebaseCrashlytics.getInstance().log(message)
        }
    }

    actual fun recordException(throwable: Throwable) {
        runCatching {
            FirebaseCrashlytics.getInstance().recordException(throwable)
        }
    }

    actual fun setCustomKey(key: String, value: String) {
        runCatching {
            FirebaseCrashlytics.getInstance().setCustomKey(key, value)
        }
    }

    actual fun setUserId(userId: String) {
        runCatching {
            FirebaseCrashlytics.getInstance().setUserId(userId)
        }
    }
}
