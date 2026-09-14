package com.sabeomnim.app.core.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
import platform.AVFoundation.pause
import platform.AVFoundation.rate
import platform.AVFoundation.seekToTime
import platform.AVKit.AVPlayerViewController
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSURL
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformVideoPlayer(
    videoUrl: String,
    isPlaying: Boolean,
    playbackSpeed: Float,
    seekToMs: Long?,
    onProgressUpdate: (currentMs: Long, durationMs: Long) -> Unit,
    modifier: Modifier
) {
    if (videoUrl.isBlank()) {
        Box(modifier = modifier.background(Color.Black), contentAlignment = Alignment.Center) {
            Text("No video source", color = Color.White)
        }
        return
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val url = NSURL.URLWithString(videoUrl)
            val player = if (url != null) AVPlayer(uRL = url) else AVPlayer()
            val controller = AVPlayerViewController().apply {
                this.player = player
                this.showsPlaybackControls = false
            }
            controller.view
        },
        update = { view ->
            // AVPlayer update logic on iOS
        }
    )
}
