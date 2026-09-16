package com.sabeomnim.app.core.platform

import androidx.compose.runtime.Composable

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    UNSPECIFIED
}

@Composable
expect fun LockScreenOrientation(orientation: ScreenOrientation)

@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)
