package com.sabeomnim.app.presentation.poomsae

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.sabeomnim.app.core.audio.rememberAudioService
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.designsystem.KukkiwonGold
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
import com.sabeomnim.app.core.i18n.AppLanguage
import com.sabeomnim.app.core.i18n.AppStrings
import com.sabeomnim.app.core.i18n.LocalAppLanguage
import com.sabeomnim.app.core.platform.BackHandler
import com.sabeomnim.app.core.platform.LockScreenOrientation
import com.sabeomnim.app.core.platform.ScreenOrientation
import com.sabeomnim.app.core.player.PlatformVideoPlayer
import com.sabeomnim.app.data.models.Poomsae
import com.sabeomnim.app.data.models.PoomsaeStep
import com.sabeomnim.app.data.models.VideoAngle
import com.sabeomnim.app.data.repository.PoomsaeRepository

enum class PoomsaeDisplayMode(val label: String, val icon: ImageVector) {
    VIDEO("Video", Icons.Default.PlayCircle),
    CHEAT_SHEET("Diagram", Icons.Default.Map)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoomsaePlayerScreen(
    initialPoomsaeId: String = "taegeuk_1",
    onOpenSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val audioService = rememberAudioService()
    var selectedPoomsae by remember {
        mutableStateOf(PoomsaeRepository.getPoomsaeById(initialPoomsaeId) ?: PoomsaeRepository.poomsaeTaegeuk1)
    }
    var displayMode by remember { mutableStateOf(PoomsaeDisplayMode.VIDEO) }
    var selectedAngle by remember { mutableStateOf(VideoAngle.FRONT) }
    var isPlaying by remember { mutableStateOf(true) }
    var playbackSpeed by remember { mutableStateOf(1.0f) }
    var currentPositionMs by remember { mutableStateOf(0L) }
    var durationMs by remember { mutableStateOf(0L) }
    var seekTargetMs by remember { mutableStateOf<Long?>(null) }
    var isStepLoopEnabled by remember { mutableStateOf(false) }
    var isFullScreen by remember { mutableStateOf(false) }

    val activeUrl = if (selectedAngle == VideoAngle.FRONT) {
        selectedPoomsae.frontVideoUrl
    } else {
        selectedPoomsae.sideVideoUrl
    }

    // Identify the active step based on current position
    val currentStep: PoomsaeStep? = remember(selectedPoomsae, currentPositionMs) {
        selectedPoomsae.steps.firstOrNull { step ->
            currentPositionMs >= step.startTimeMs && currentPositionMs < step.endTimeMs
        } ?: selectedPoomsae.steps.lastOrNull { currentPositionMs >= it.startTimeMs } ?: selectedPoomsae.steps.firstOrNull()
    }

    // Step loop handler
    LaunchedEffect(currentPositionMs, isStepLoopEnabled, currentStep) {
        if (isStepLoopEnabled && currentStep != null && currentPositionMs >= currentStep.endTimeMs) {
            seekTargetMs = currentStep.startTimeMs
        }
    }

    if (isFullScreen) {
        PoomsaeLandscapeFullscreenPlayer(
            poomsae = selectedPoomsae,
            videoUrl = activeUrl,
            isPlaying = isPlaying,
            playbackSpeed = playbackSpeed,
            currentPositionMs = currentPositionMs,
            durationMs = durationMs,
            seekTargetMs = seekTargetMs,
            selectedAngle = selectedAngle,
            currentStep = currentStep,
            isStepLoopEnabled = isStepLoopEnabled,
            lang = lang,
            onPlayPauseToggle = { isPlaying = !isPlaying },
            onSpeedChange = { playbackSpeed = it },
            onAngleChange = { selectedAngle = it },
            onSeekTo = { seekTargetMs = it },
            onStepLoopToggle = { isStepLoopEnabled = !isStepLoopEnabled },
            onProgressUpdate = { current, dur ->
                currentPositionMs = current
                if (dur > 0) durationMs = dur
                if (seekTargetMs != null && kotlin.math.abs(current - (seekTargetMs ?: 0L)) < 500) {
                    seekTargetMs = null
                }
            },
            onAudioSpeak = { audioService.speak(it) },
            onExitFullscreen = { isFullScreen = false }
        )
        return
    }

    when (displayMode) {
        PoomsaeDisplayMode.CHEAT_SHEET -> {
            TaegeukCheatSheetView(
                poomsae = selectedPoomsae,
                lang = lang,
                headerContent = {
                    PoomsaeHeader(
                        selectedPoomsae = selectedPoomsae,
                        lang = lang,
                        onSelectPoomsae = {
                            selectedPoomsae = it
                            currentPositionMs = 0L
                            seekTargetMs = 0L
                        },
                        displayMode = displayMode,
                        onDisplayModeChange = { displayMode = it },
                        onOpenSettings = onOpenSettings,
                        modifier = Modifier
                    )
                },
                modifier = modifier.fillMaxSize()
            )
        }

        PoomsaeDisplayMode.VIDEO -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 12.dp, bottom = 32.dp)
            ) {
                PoomsaeHeader(
                    selectedPoomsae = selectedPoomsae,
                    lang = lang,
                    onSelectPoomsae = {
                        selectedPoomsae = it
                        currentPositionMs = 0L
                        seekTargetMs = 0L
                    },
                    displayMode = displayMode,
                    onDisplayModeChange = { displayMode = it },
                    onOpenSettings = onOpenSettings,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Angle Switcher Bar & Speed Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { selectedAngle = VideoAngle.FRONT },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAngle == VideoAngle.FRONT) TaegeukBlue else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (selectedAngle == VideoAngle.FRONT) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Front", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = { selectedAngle = VideoAngle.SIDE },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAngle == VideoAngle.SIDE) TaegeukRed else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (selectedAngle == VideoAngle.SIDE) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Side", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Speed rate toggle
                    Box {
                        var speedMenuExpanded by remember { mutableStateOf(false) }
                        OutlinedButton(
                            onClick = { speedMenuExpanded = true },
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("${playbackSpeed}x", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        DropdownMenu(
                            expanded = speedMenuExpanded,
                            onDismissRequest = { speedMenuExpanded = false }
                        ) {
                            listOf(0.25f, 0.5f, 0.75f, 1.0f).forEach { speed ->
                                DropdownMenuItem(
                                    text = { Text("${speed}x") },
                                    onClick = {
                                        playbackSpeed = speed
                                        speedMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Video Player Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                ) {
                    PlatformVideoPlayer(
                        videoUrl = activeUrl,
                        isPlaying = isPlaying,
                        playbackSpeed = playbackSpeed,
                        seekToMs = seekTargetMs,
                        initialPositionMs = currentPositionMs,
                        onProgressUpdate = { current, dur ->
                            currentPositionMs = current
                            if (dur > 0) durationMs = dur
                            if (seekTargetMs != null && kotlin.math.abs(current - (seekTargetMs ?: 0L)) < 500) {
                                seekTargetMs = null
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // Kihap Banner indicator when active step has a shout
                    if (currentStep?.isKihap == true) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = TaegeukRed
                        ) {
                            Text(
                                text = "⚡ KIHAP!",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Fullscreen Landscape Toggle Button
                    IconButton(
                        onClick = { isFullScreen = true },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(36.dp)
                            .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Fullscreen,
                            contentDescription = "Fullscreen Landscape",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Precision Scrubber & Step Navigation Controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    // Slider
                    val effectiveDuration = if (durationMs > 0) durationMs else 60000L
                    Slider(
                        value = currentPositionMs.coerceIn(0L, effectiveDuration).toFloat(),
                        onValueChange = { newPos ->
                            seekTargetMs = newPos.toLong()
                        },
                        valueRange = 0f..effectiveDuration.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Time Readout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(currentPositionMs),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatTime(effectiveDuration),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Playback Control Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Step
                        IconButton(
                            onClick = {
                                val currentIdx = currentStep?.stepIndex ?: 1
                                val prevStep = selectedPoomsae.steps.firstOrNull { it.stepIndex == currentIdx - 1 }
                                    ?: selectedPoomsae.steps.firstOrNull()
                                prevStep?.let { seekTargetMs = it.startTimeMs }
                            }
                        ) {
                            Icon(Icons.Default.SkipPrevious, contentDescription = "Previous Step", modifier = Modifier.size(28.dp))
                        }

                        // Replay / Loop Current Step
                        IconButton(
                            onClick = {
                                isStepLoopEnabled = !isStepLoopEnabled
                            }
                        ) {
                            Icon(
                                Icons.Default.Repeat,
                                contentDescription = "Loop Step",
                                tint = if (isStepLoopEnabled) TaegeukBlue else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Play / Pause
                        FilledIconButton(
                            onClick = { isPlaying = !isPlaying },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = TaegeukBlue
                            ),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Rewind 2s
                        IconButton(
                            onClick = {
                                seekTargetMs = (currentPositionMs - 2000L).coerceAtLeast(0L)
                            }
                        ) {
                            Icon(Icons.Default.Replay5, contentDescription = "Back 2s")
                        }

                        // Next Step
                        IconButton(
                            onClick = {
                                val currentIdx = currentStep?.stepIndex ?: 1
                                val nextStep = selectedPoomsae.steps.firstOrNull { it.stepIndex == currentIdx + 1 }
                                    ?: selectedPoomsae.steps.lastOrNull()
                                nextStep?.let { seekTargetMs = it.startTimeMs }
                            }
                        ) {
                            Icon(Icons.Default.SkipNext, contentDescription = "Next Step", modifier = Modifier.size(28.dp))
                        }
                    }
                }

                // Real-time Move Subtitle Heads-Up Display (HUD)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = TaegeukBlue
                            ) {
                                Text(
                                    text = AppStrings.stepLabel(lang, currentStep?.stepIndex ?: 1, selectedPoomsae.movementCount),
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            if (isStepLoopEnabled) {
                                Text(
                                    text = AppStrings.stepLoopActive(lang),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TaegeukBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentStep?.romanized ?: (if (lang == AppLanguage.DANISH) "Junbi (Klarstilling)" else "Junbi (Ready)"),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        val stepDescription = if (lang == AppLanguage.DANISH && currentStep?.danish != null) {
                            currentStep.danish!!
                        } else if (lang == AppLanguage.DANISH && currentStep == null) {
                            "Indtag naturlig klarstilling"
                        } else {
                            currentStep?.english ?: "Assume natural ready position"
                        }
                        Text(
                            text = stepDescription,
                            fontSize = 13.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (lang == AppLanguage.ENGLISH && currentStep?.danish != null) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "🇩🇰 ${currentStep.danish}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            currentStep?.stance?.let {
                                AssistChip(
                                    onClick = {},
                                    label = { Text(AppStrings.stanceLabel(lang, it), fontSize = 11.sp) }
                                )
                            }
                            currentStep?.technique?.let {
                                AssistChip(
                                    onClick = {},
                                    label = { Text(AppStrings.moveLabel(lang, it), fontSize = 11.sp) }
                                )
                            }
                        }

                        val coachingTip = if (lang == AppLanguage.DANISH && currentStep?.coachingTipDanish != null) {
                            currentStep.coachingTipDanish
                        } else {
                            currentStep?.coachingTip
                        }
                        coachingTip?.let { tip ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = KukkiwonGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = tip,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Step List Directory Header
                Text(
                    text = AppStrings.movementChecklist(lang, selectedPoomsae.steps.size),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Interactive Step Items
                selectedPoomsae.steps.forEach { step ->
                    val isCurrent = step.stepIndex == currentStep?.stepIndex
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                seekTargetMs = step.startTimeMs
                                audioService.speak(step.korean)
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrent) TaegeukBlue.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                        ),
                        border = if (isCurrent) borderForCurrentStep() else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isCurrent) TaegeukBlue else MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${step.stepIndex}",
                                    color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = step.romanized,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    if (step.isKihap) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = TaegeukRed
                                        ) {
                                            Text(
                                                text = "KIHAP",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                                val itemDescription = if (lang == AppLanguage.DANISH && step.danish != null) {
                                    step.danish
                                } else {
                                    step.english
                                }
                                Text(
                                    text = itemDescription,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Jump",
                                tint = if (isCurrent) TaegeukBlue else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun PoomsaeHeader(
    selectedPoomsae: Poomsae,
    lang: AppLanguage,
    onSelectPoomsae: (Poomsae) -> Unit,
    displayMode: PoomsaeDisplayMode,
    onDisplayModeChange: (PoomsaeDisplayMode) -> Unit,
    onOpenSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Title & Trigram Subtitle
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = selectedPoomsae.nameRomanized,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            val subText = if (lang == AppLanguage.DANISH && selectedPoomsae.nameDanish != null) {
                "${selectedPoomsae.nameDanish} • ${selectedPoomsae.trigramSymbol}"
            } else {
                "${selectedPoomsae.nameEnglish} • ${selectedPoomsae.trigramSymbol}"
            }
            Text(
                text = subText,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Taegeuk Form Selector Carousel
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(PoomsaeRepository.getAllPoomsae()) { poomsae ->
                val isSelected = poomsae.id == selectedPoomsae.id
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectPoomsae(poomsae) },
                    label = { Text("Taegeuk ${poomsae.number}") },
                    leadingIcon = {
                        Text(poomsae.trigramSymbol, fontSize = 14.sp)
                    }
                )
            }
        }

        // View Mode Tab Bar: Video vs Cheat Sheet
        TabRow(
            selectedTabIndex = displayMode.ordinal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            indicator = {}
        ) {
            PoomsaeDisplayMode.entries.forEach { mode ->
                val isSelected = displayMode == mode
                Tab(
                    selected = isSelected,
                    onClick = { onDisplayModeChange(mode) },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = mode.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (isSelected) TaegeukBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            val tabTitle = if (mode == PoomsaeDisplayMode.VIDEO) {
                                AppStrings.modeVideo(lang)
                            } else {
                                AppStrings.modeCheatSheet(lang)
                            }
                            Text(
                                text = tabTitle,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                color = if (isSelected) TaegeukBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun borderForCurrentStep() = androidx.compose.foundation.BorderStroke(1.5.dp, TaegeukBlue)

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}

@Composable
fun PoomsaeLandscapeFullscreenPlayer(
    poomsae: Poomsae,
    videoUrl: String,
    isPlaying: Boolean,
    playbackSpeed: Float,
    currentPositionMs: Long,
    durationMs: Long,
    seekTargetMs: Long?,
    selectedAngle: VideoAngle,
    currentStep: PoomsaeStep?,
    isStepLoopEnabled: Boolean,
    lang: AppLanguage,
    onPlayPauseToggle: () -> Unit,
    onSpeedChange: (Float) -> Unit,
    onAngleChange: (VideoAngle) -> Unit,
    onSeekTo: (Long) -> Unit,
    onStepLoopToggle: () -> Unit,
    onProgressUpdate: (Long, Long) -> Unit,
    onAudioSpeak: (String) -> Unit,
    onExitFullscreen: () -> Unit
) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)
    BackHandler(enabled = true) {
        onExitFullscreen()
    }

    var showControls by remember { mutableStateOf(true) }

    // Auto-hide controls after 4 seconds of playback
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            kotlinx.coroutines.delay(4000)
            showControls = false
        }
    }

    val effectiveDuration = if (durationMs > 0) durationMs else 60000L

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showControls = !showControls
            }
    ) {
        PlatformVideoPlayer(
            videoUrl = videoUrl,
            isPlaying = isPlaying,
            playbackSpeed = playbackSpeed,
            seekToMs = seekTargetMs,
            initialPositionMs = currentPositionMs,
            onProgressUpdate = onProgressUpdate,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Top gradient bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.85f), Color.Transparent)
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                onClick = onExitFullscreen,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Exit Fullscreen",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                val title = if (lang == AppLanguage.DANISH && poomsae.nameDanish != null) {
                                    poomsae.nameDanish
                                } else {
                                    poomsae.nameEnglish
                                }
                                Text(
                                    text = "$title (${poomsae.nameKorean})",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Step ${currentStep?.stepIndex ?: 1} / ${poomsae.movementCount}",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Angle & Speed & Exit buttons
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Camera Angle Switcher
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Row(modifier = Modifier.padding(2.dp)) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (selectedAngle == VideoAngle.FRONT) TaegeukBlue else Color.Transparent,
                                        modifier = Modifier.clickable { onAngleChange(VideoAngle.FRONT) }
                                    ) {
                                        Text(
                                            text = "Front 0°",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (selectedAngle == VideoAngle.SIDE) TaegeukRed else Color.Transparent,
                                        modifier = Modifier.clickable { onAngleChange(VideoAngle.SIDE) }
                                    ) {
                                        Text(
                                            text = "Side 90°",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            // Speed Selector
                            var showSpeedMenu by remember { mutableStateOf(false) }
                            Box {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.15f),
                                    modifier = Modifier.clickable { showSpeedMenu = true }
                                ) {
                                    Text(
                                        text = "${playbackSpeed}x",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = showSpeedMenu,
                                    onDismissRequest = { showSpeedMenu = false }
                                ) {
                                    listOf(0.25f, 0.5f, 0.75f, 1.0f).forEach { spd ->
                                        DropdownMenuItem(
                                            text = { Text("${spd}x") },
                                            onClick = {
                                                onSpeedChange(spd)
                                                showSpeedMenu = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Fullscreen Exit Button
                            IconButton(
                                onClick = onExitFullscreen,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.FullscreenExit,
                                    contentDescription = "Exit Fullscreen",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Center Play/Pause & Step Skip Buttons
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    // Previous Step
                    IconButton(
                        onClick = {
                            val currentIdx = currentStep?.stepIndex ?: 1
                            val prev = poomsae.steps.find { it.stepIndex == currentIdx - 1 }
                                ?: poomsae.steps.firstOrNull()
                            prev?.let { onSeekTo(it.startTimeMs) }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.SkipPrevious,
                            contentDescription = "Previous Step",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Play / Pause
                    IconButton(
                        onClick = onPlayPauseToggle,
                        modifier = Modifier
                            .size(64.dp)
                            .background(TaegeukBlue.copy(alpha = 0.85f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Next Step
                    IconButton(
                        onClick = {
                            val currentIdx = currentStep?.stepIndex ?: 1
                            val next = poomsae.steps.find { it.stepIndex == currentIdx + 1 }
                            next?.let { onSeekTo(it.startTimeMs) }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.SkipNext,
                            contentDescription = "Next Step",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Bottom gradient bar with step details & scrubber
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Current Step HUD Info
                        if (currentStep != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (currentStep.isKihap) TaegeukRed else TaegeukBlue,
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${currentStep.stepIndex}",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = currentStep.romanized,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "• ${currentStep.korean}",
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 12.sp
                                    )
                                    if (currentStep.isKihap) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = TaegeukRed
                                        ) {
                                            Text(
                                                text = "⚡ KIHAP",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Audio pronunciation button
                                    IconButton(
                                        onClick = { onAudioSpeak(currentStep.korean) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Speak Korean",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Step Loop toggle
                                    IconButton(
                                        onClick = onStepLoopToggle,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Repeat,
                                            contentDescription = "Loop Step",
                                            tint = if (isStepLoopEnabled) TaegeukBlue else Color.White.copy(alpha = 0.6f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Scrubber Slider Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatTime(currentPositionMs),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Slider(
                                value = currentPositionMs.coerceIn(0L, effectiveDuration).toFloat(),
                                onValueChange = { onSeekTo(it.toLong()) },
                                valueRange = 0f..effectiveDuration.toFloat(),
                                colors = SliderDefaults.colors(
                                    thumbColor = TaegeukBlue,
                                    activeTrackColor = TaegeukBlue,
                                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            )
                            Text(
                                text = formatTime(effectiveDuration),
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

