package com.sabeomnim.app.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.sabeomnim.app.core.ui.belt.UnfoldingBeltView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.audio.rememberAudioService
import com.sabeomnim.app.core.designsystem.KukkiwonGold
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
import com.sabeomnim.app.core.i18n.AppLanguage
import com.sabeomnim.app.core.i18n.AppStrings
import com.sabeomnim.app.core.i18n.LocalAppLanguage
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.repository.BeltRepository
import com.sabeomnim.app.data.repository.PoomsaeRepository
import com.sabeomnim.app.data.repository.QuizRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeltDashboardScreen(
    selectedBelt: BeltRank,
    onBeltSelected: (BeltRank) -> Unit,
    onOpenPoomsae: (String) -> Unit,
    onOpenQuiz: (BeltRank) -> Unit,
    onOpenDictionary: () -> Unit,
    onOpenSettings: () -> Unit = {}
) {
    val lang = LocalAppLanguage.current
    val audioService = rememberAudioService()
    var currentlyPlayingTechName by remember { mutableStateOf<String?>(null) }
    val curriculum = BeltRepository.getCurriculum(selectedBelt)
    val poomsae = PoomsaeRepository.getPoomsaeForBelt(selectedBelt)
    val quizCount = QuizRepository.getQuestionsForBelt(selectedBelt).size

    var lastAnimatedBelt by rememberSaveable { mutableStateOf<String?>(null) }
    val shouldAnimateBelt = (lastAnimatedBelt != selectedBelt.name)

    LaunchedEffect(selectedBelt) {
        lastAnimatedBelt = selectedBelt.name
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            item(key = "belt_selector") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = AppStrings.selectBeltGrade(lang),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                BeltSelectorRow(
                    selectedBelt = selectedBelt,
                    onBeltSelected = onBeltSelected
                )
            }

            // Current Belt Hero Card with Unfolding Belt in the side
            item(key = "hero_belt_card") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(selectedBelt.colorHex).copy(alpha = 0.22f)
                    ),
                    border = BorderStroke(1.5.dp, Color(selectedBelt.accentColorHex).copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp)
                        ) {
                            Text(
                                text = selectedBelt.localizedGrade(lang),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(selectedBelt.accentColorHex)
                            )
                            Text(
                                text = selectedBelt.localizedTitle(lang),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = selectedBelt.romanized,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = curriculum.localizedMeaning(lang),
                                fontSize = 13.5.sp,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BadgeInfo(label = AppStrings.trainingLabel(lang), value = AppStrings.monthsSuffix(lang, curriculum.minimumTrainingMonths))
                                BadgeInfo(label = AppStrings.techniquesLabel(lang), value = AppStrings.requiredSuffix(lang, curriculum.techniques.size))
                            }
                        }

                        // Authentic Unfolding Taekwondo Belt in the side
                        UnfoldingBeltView(
                            belt = selectedBelt,
                            boxWidth = 96.dp,
                            maxBeltLength = 175.dp,
                            autoPlay = shouldAnimateBelt
                        )
                    }
                }
            }

            // Poomsae Card (if this belt has one)
            item(key = "poomsae_card") {
                if (poomsae != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenPoomsae(poomsae.id) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(TaegeukBlue),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.PlayCircle,
                                        contentDescription = "Poomsae Video",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = "${poomsae.nameRomanized} (${poomsae.trigramSymbol})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    )
                                    val poomsaeTitle = if (lang == AppLanguage.DANISH && poomsae.nameDanish != null) poomsae.nameDanish else poomsae.nameEnglish
                                    Text(
                                        text = "$poomsaeTitle • ${poomsae.movementCount} ${AppStrings.moves(lang)}",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = AppStrings.dualAngleVideoBadge(lang),
                                        fontSize = 12.sp,
                                        color = TaegeukBlue,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = "Open")
                        }
                    }
                }
            }

            // Belt Grading Theory Quiz Card
            item(key = "quiz_card") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenQuiz(selectedBelt) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(TaegeukRed),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Quiz,
                                    contentDescription = "Grading Quiz",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = AppStrings.gradingExamTitle(lang),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "$quizCount ${AppStrings.questionsCount(lang, selectedBelt.localizedGrade(lang))}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = "Start Quiz")
                    }
                }
            }

            // Required Techniques Breakdown Header
            item(key = "techniques_header") {
                Text(
                    text = "${AppStrings.requiredTechniques(lang)} (${selectedBelt.localizedGrade(lang)})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = curriculum.techniques,
                key = { it.nameRomanized }
            ) { tech ->
                val isPlaying = currentlyPlayingTechName == tech.nameRomanized
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            currentlyPlayingTechName = tech.nameRomanized
                            audioService.speak(tech.nameHangul)
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPlaying) TaegeukBlue.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                    ),
                    border = if (isPlaying) {
                        BorderStroke(1.5.dp, TaegeukBlue)
                    } else {
                        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = TaegeukBlue.copy(alpha = 0.12f),
                                contentColor = TaegeukBlue
                            ) {
                                Text(
                                    text = tech.localizedType(lang),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = tech.nameRomanized,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (isPlaying) TaegeukBlue else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = tech.localizedName(lang),
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tech.localizedDescription(lang),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                currentlyPlayingTechName = tech.nameRomanized
                                audioService.speak(tech.nameHangul)
                            },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Udtal",
                                tint = if (isPlaying) TaegeukRed else TaegeukBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

@Composable
fun BeltSelectorRow(
    selectedBelt: BeltRank,
    onBeltSelected: (BeltRank) -> Unit
) {
    val lang = LocalAppLanguage.current
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(BeltRank.entries) { belt ->
            val isSelected = belt == selectedBelt
            FilterChip(
                selected = isSelected,
                onClick = { onBeltSelected(belt) },
                label = {
                    Text(
                        text = belt.localizedGrade(lang),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                leadingIcon = {
                    com.sabeomnim.app.core.ui.belt.BeltMiniIcon(belt = belt)
                }
            )
        }
    }
}

@Composable
fun BadgeInfo(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}
