package com.sabeomnim.app.core.logging

actual object CrashlyticsLogger {
    actual fun log(message: String) {
        println("[Crashlytics iOS] $message")
    }

    actual fun recordException(throwable: Throwable) {
        println("[Crashlytics iOS] Exception: ${throwable.message}")
        throwable.printStackTrace()
    }

    actual fun setCustomKey(key: String, value: String) {
        println("[Crashlytics iOS] Key: $key = $value")
    }

    actual fun setUserId(userId: String) {
        println("[Crashlytics iOS] UserId: $userId")
    }
}
