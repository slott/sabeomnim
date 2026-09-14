package com.sabeomnim.app.core.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(UnstableApi::class)
@Composable
actual fun PlatformVideoPlayer(
    videoUrl: String,
    isPlaying: Boolean,
    playbackSpeed: Float,
    seekToMs: Long?,
    onProgressUpdate: (currentMs: Long, durationMs: Long) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    // Handle video URL updates (including angle switching)
    LaunchedEffect(videoUrl) {
        if (videoUrl.isNotBlank()) {
            val currentPos = exoPlayer.currentPosition
            val wasPlaying = exoPlayer.isPlaying
            val mediaItem = MediaItem.fromUri(videoUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            if (currentPos > 0) {
                exoPlayer.seekTo(currentPos)
            }
            exoPlayer.playWhenReady = wasPlaying || isPlaying
        }
    }

    // Handle play/pause
    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    // Handle playback speed
    LaunchedEffect(playbackSpeed) {
        exoPlayer.playbackParameters = PlaybackParameters(playbackSpeed)
    }

    // Handle external seeks
    LaunchedEffect(seekToMs) {
        seekToMs?.let { ms ->
            exoPlayer.seekTo(ms)
        }
    }

    // Track playback progress
    LaunchedEffect(exoPlayer) {
        while (isActive) {
            val current = exoPlayer.currentPosition
            val duration = exoPlayer.duration.coerceAtLeast(0L)
            onProgressUpdate(current, duration)
            delay(100L)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false // We provide custom precision controls
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )
}
