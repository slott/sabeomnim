package com.sabeomnim.app.core.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformVideoPlayer(
    videoUrl: String,
    isPlaying: Boolean,
    playbackSpeed: Float,
    seekToMs: Long?,
    onProgressUpdate: (currentMs: Long, durationMs: Long) -> Unit,
    modifier: Modifier = Modifier
)
