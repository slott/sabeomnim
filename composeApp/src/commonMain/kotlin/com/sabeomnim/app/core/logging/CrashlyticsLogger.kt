package com.sabeomnim.app.core.logging

expect object CrashlyticsLogger {
    fun log(message: String)
    fun recordException(throwable: Throwable)
    fun setCustomKey(key: String, value: String)
    fun setUserId(userId: String)
}
