package com.sabeomnim.app.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.Foundation.NSNumber
import platform.Foundation.setValue
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

@Composable
actual fun LockScreenOrientation(orientation: ScreenOrientation) {
    DisposableEffect(orientation) {
        val targetOrientation: Long = when (orientation) {
            ScreenOrientation.LANDSCAPE -> UIDeviceOrientation.UIDeviceOrientationLandscapeLeft.value
            ScreenOrientation.PORTRAIT -> UIDeviceOrientation.UIDeviceOrientationPortrait.value
            ScreenOrientation.UNSPECIFIED -> UIDeviceOrientation.UIDeviceOrientationUnknown.value
        }
        UIDevice.currentDevice.setValue(
            NSNumber(long = targetOrientation),
            forKey = "orientation"
        )
        onDispose {
            UIDevice.currentDevice.setValue(
                NSNumber(long = UIDeviceOrientation.UIDeviceOrientationPortrait.value),
                forKey = "orientation"
            )
        }
    }
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op on iOS
}
