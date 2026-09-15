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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.designsystem.KukkiwonGold
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
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
    onOpenDictionary: () -> Unit
) {
    val curriculum = BeltRepository.getCurriculum(selectedBelt)
    val poomsae = PoomsaeRepository.getPoomsaeForBelt(selectedBelt)
    val quizCount = QuizRepository.getQuestionsForBelt(selectedBelt).size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sabeomnim",
                        fontWeight = FontWeight.Bold,
                        color = TaegeukRed,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select Belt Grade",
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
            item {
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
                                text = selectedBelt.gradeText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(selectedBelt.accentColorHex)
                            )
                            Text(
                                text = selectedBelt.title,
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
                                text = curriculum.meaning,
                                fontSize = 13.5.sp,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BadgeInfo(label = "Training", value = "${curriculum.minimumTrainingMonths} Mos")
                                BadgeInfo(label = "Techniques", value = "${curriculum.techniques.size} Req")
                            }
                        }

                        // Authentic Unfolding Taekwondo Belt in the side
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UnfoldingBeltView(
                                belt = selectedBelt,
                                beltWidth = 40.dp,
                                maxBeltLength = 175.dp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Drag tails to swing",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Poomsae Card (if this belt has one)
            item {
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
                                    Text(
                                        text = "${poomsae.nameEnglish} • ${poomsae.movementCount} Moves",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "Dual-Angle HD Video + Subtitles",
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
            item {
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
                                    text = "Grading Theory Exam",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "$quizCount Questions for ${selectedBelt.gradeText}",
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
            item {
                Text(
                    text = "Required Techniques for ${selectedBelt.gradeText}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(curriculum.techniques) { tech ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when (tech.type) {
                                "Kick" -> TaegeukRed.copy(alpha = 0.15f)
                                "Block" -> TaegeukBlue.copy(alpha = 0.15f)
                                "Strike" -> KukkiwonGold.copy(alpha = 0.2f)
                                else -> MaterialTheme.colorScheme.secondaryContainer
                            }
                        ) {
                            Text(
                                text = tech.type,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tech.nameRomanized,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = tech.nameEnglish,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tech.description,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
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
}

@Composable
fun BeltSelectorRow(
    selectedBelt: BeltRank,
    onBeltSelected: (BeltRank) -> Unit
) {
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
                        text = belt.gradeText,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(Color(belt.colorHex))
                            .border(1.dp, Color(belt.accentColorHex), CircleShape)
                    )
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
