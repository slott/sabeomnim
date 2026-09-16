package com.sabeomnim.app.presentation.poomsae

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sabeomnim.app.core.audio.rememberAudioService
import com.sabeomnim.app.core.designsystem.KukkiwonGold
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
import com.sabeomnim.app.core.i18n.AppLanguage
import com.sabeomnim.app.core.i18n.AppStrings
import com.sabeomnim.app.core.i18n.LocalAppLanguage
import com.sabeomnim.app.data.models.Poomsae
import com.sabeomnim.app.data.models.PoomsaeStep
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaegeukCheatSheetView(
    poomsae: Poomsae,
    modifier: Modifier = Modifier,
    lang: AppLanguage = LocalAppLanguage.current,
    headerContent: (@Composable () -> Unit)? = null
) {
    var isFullScreenOpen by remember { mutableStateOf(false) }
    val audioService = rememberAudioService()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (headerContent != null) {
            item(key = "header_content") {
                headerContent()
            }
        }

        // Form Overview & Trigram Philosophy Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TaegeukBlue.copy(alpha = 0.08f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, TaegeukBlue.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text(
                                text = AppStrings.cheatSheetTitle(lang, poomsae.number),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TaegeukBlue
                            )
                            val subTitle = if (lang == AppLanguage.DANISH && poomsae.nameDanish != null) poomsae.nameDanish else poomsae.nameEnglish
                            Text(
                                text = "${poomsae.nameRomanized} • $subTitle",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Trigram Badge
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = TaegeukBlue,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = poomsae.trigramSymbol,
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    val desc = if (lang == AppLanguage.DANISH && poomsae.descriptionDanish != null) poomsae.descriptionDanish else poomsae.description
                    Text(
                        text = desc,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "🥋 ${poomsae.beltRank.localizedGrade(lang)} (${poomsae.beltRank.localizedTitle(lang)})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "👣 ${AppStrings.movementsCount(lang, poomsae.movementCount)}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = TaegeukRed.copy(alpha = 0.15f)
                        ) {
                            val kihapStepIdx = poomsae.steps.lastOrNull { it.isKihap }?.stepIndex ?: poomsae.movementCount
                            Text(
                                text = AppStrings.kihapStep(lang, kihapStepIdx),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TaegeukRed,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Interactive Cheat Sheet Visual Diagram
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isFullScreenOpen = true },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Map,
                            contentDescription = null,
                            tint = TaegeukBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.diagramTitle(lang),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cheat sheet image container (opens fullscreen on tap)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(getTaegeukCheatSheetResource(poomsae.number)),
                            contentDescription = "Taegeuk ${poomsae.number} Diagram Cheat Sheet",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        // Section Title: Choreography Step Breakdown
        item {
            Text(
                text = AppStrings.stepDirectoryTitle(lang),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Ready Position Info Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { audioService.speak("준비서기") },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(TaegeukBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(AppStrings.readyBadge(lang), color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(AppStrings.readyTitle(lang), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(AppStrings.readyDescription(lang), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { audioService.speak("준비서기") }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Hear Junbi", tint = TaegeukBlue, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        // Step by Step Cards
        items(poomsae.steps, key = { it.stepIndex }) { step ->
            CheatSheetStepCard(step = step, lang = lang, onAudioPlay = { audioService.speak(step.korean) })
        }

        // Return to Ready (Baro) Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { audioService.speak("바로") },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(TaegeukRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(AppStrings.baroBadge(lang), color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(AppStrings.baroTitle(lang), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(AppStrings.baroDescription(lang), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { audioService.speak("바로") }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Hear Baro", tint = TaegeukRed, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(36.dp))
        }
    }

    // Full-screen Dialog Modal
    if (isFullScreenOpen) {
        Dialog(
            onDismissRequest = { isFullScreenOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var dialogScale by remember { mutableStateOf(1f) }
            var dialogOffset by remember { mutableStateOf(Offset.Zero) }

            val dialogTransform = rememberTransformableState { zoomChange, panChange, _ ->
                dialogScale = (dialogScale * zoomChange).coerceIn(1f, 5f)
                if (dialogScale > 1f) {
                    val maxOffset = (dialogScale - 1f) * 600f
                    dialogOffset = Offset(
                        x = (dialogOffset.x + panChange.x).coerceIn(-maxOffset, maxOffset),
                        y = (dialogOffset.y + panChange.y).coerceIn(-maxOffset, maxOffset)
                    )
                } else {
                    dialogOffset = Offset.Zero
                }
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(getTaegeukCheatSheetResource(poomsae.number)),
                        contentDescription = "Fullscreen Taegeuk ${poomsae.number} Diagram",
                        modifier = Modifier
                            .fillMaxSize()
                            .transformable(state = dialogTransform)
                            .graphicsLayer(
                                scaleX = dialogScale,
                                scaleY = dialogScale,
                                translationX = dialogOffset.x,
                                translationY = dialogOffset.y
                            ),
                        contentScale = ContentScale.Fit
                    )

                    // Close Button
                    IconButton(
                        onClick = { isFullScreenOpen = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }

                    // Reset button
                    if (dialogScale > 1.05f) {
                        IconButton(
                            onClick = {
                                dialogScale = 1f
                                dialogOffset = Offset.Zero
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset Zoom", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CheatSheetStepCard(
    step: PoomsaeStep,
    lang: AppLanguage,
    onAudioPlay: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAudioPlay() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(if (step.isKihap) TaegeukRed else TaegeukBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${step.stepIndex}",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = step.romanized,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (step.isKihap) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = TaegeukRed
                            ) {
                                Text(
                                    text = "⚡ KIHAP",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    val stepDesc = if (lang == AppLanguage.DANISH && step.danish != null) step.danish else step.english
                    Text(
                        text = stepDesc,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Korean pronunciation audio button
                IconButton(onClick = onAudioPlay) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Hear Step",
                        tint = TaegeukBlue,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Stance & Technique chips
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = AppStrings.stanceLabel(lang, step.stance),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = AppStrings.moveLabel(lang, step.technique),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Coaching deduction tips
            val tip = if (lang == AppLanguage.DANISH && step.coachingTipDanish != null) step.coachingTipDanish else step.coachingTip
            tip?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = KukkiwonGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
