package com.sabeomnim.app.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.audio.rememberAudioService
import com.sabeomnim.app.core.designsystem.KukkiwonGold
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.QuizQuestion
import com.sabeomnim.app.data.repository.QuizRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    initialBelt: BeltRank = BeltRank.WHITE
) {
    var selectedBelt by remember { mutableStateOf(initialBelt) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val audioService = rememberAudioService()

    val questions: List<QuizQuestion> = remember(selectedBelt) {
        val list = QuizRepository.getQuestionsForBelt(selectedBelt)
        if (list.isEmpty()) QuizRepository.getAllQuestions().take(5) else list
    }

    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    fun resetQuiz() {
        currentQuestionIndex = 0
        selectedOptionIndex = null
        score = 0
        isAnswerSubmitted = false
        isQuizCompleted = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("승급 심사 이론 시험", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TaegeukRed)
                        Text("${selectedBelt.title} (${selectedBelt.gradeText})", fontSize = 13.sp)
                    }
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
            // Belt Selection Row
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(BeltRank.entries) { belt ->
                        val isSelected = belt == selectedBelt
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedBelt = belt
                                resetQuiz()
                            },
                            label = { Text(belt.gradeText) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(Color(belt.colorHex))
                                        .border(1.dp, Color(belt.accentColorHex), CircleShape)
                                )
                            }
                        )
                    }
                }
            }

            if (isQuizCompleted) {
                // Completed Summary Card
                item {
                    val passScore = (questions.size * 0.70).toInt().coerceAtLeast(1)
                    val passed = score >= passScore

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (passed) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (passed) "🎉 PROMOTION READY! (합격)" else "📚 NEEDS STUDY (재시험)",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (passed) Color(0xFF2E7D32) else TaegeukRed
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Your Score: $score / ${questions.size}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            val percent = if (questions.isNotEmpty()) (score * 100) / questions.size else 0
                            Text(
                                text = "$percent% Mastery for ${selectedBelt.gradeText}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = { resetQuiz() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (passed) Color(0xFF2E7D32) else TaegeukRed
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Retake Exam")
                            }
                        }
                    }
                }
            } else if (currentQuestion != null) {
                // Progress Bar
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TaegeukBlue
                            )
                            Text(
                                text = "Score: $score",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = TaegeukBlue
                        )
                    }
                }

                // Question Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            currentQuestion.koreanTerm?.let { term ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = TaegeukBlue.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = term,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TaegeukBlue,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { audioService.speak(term) }
                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Hear Term", tint = TaegeukBlue)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Text(
                                text = currentQuestion.question,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }

                // Options List
                items(currentQuestion.options.indices.toList()) { index ->
                    val optionText = currentQuestion.options[index]
                    val isSelected = selectedOptionIndex == index
                    val isCorrectOption = index == currentQuestion.correctIndex

                    val cardColor = when {
                        !isAnswerSubmitted && isSelected -> TaegeukBlue.copy(alpha = 0.12f)
                        isAnswerSubmitted && isCorrectOption -> Color(0xFFE8F5E9)
                        isAnswerSubmitted && isSelected && !isCorrectOption -> Color(0xFFFFEBEE)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        !isAnswerSubmitted && isSelected -> TaegeukBlue
                        isAnswerSubmitted && isCorrectOption -> Color(0xFF2E7D32)
                        isAnswerSubmitted && isSelected && !isCorrectOption -> TaegeukRed
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isAnswerSubmitted) {
                                selectedOptionIndex = index
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isAnswerSubmitted && isCorrectOption -> Color(0xFF2E7D32)
                                            isAnswerSubmitted && isSelected -> TaegeukRed
                                            isSelected -> TaegeukBlue
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A'.code + index).toChar().toString(),
                                    color = if (isSelected || (isAnswerSubmitted && isCorrectOption)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = optionText,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f),
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }

                // Explanation & Next Button
                item {
                    if (isAnswerSubmitted) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Explanation:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = TaegeukBlue
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentQuestion.explanation,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                if (currentQuestionIndex + 1 < questions.size) {
                                    currentQuestionIndex++
                                    selectedOptionIndex = null
                                    isAnswerSubmitted = false
                                } else {
                                    isQuizCompleted = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(if (currentQuestionIndex + 1 < questions.size) "Next Question" else "See Results")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    } else {
                        Button(
                            onClick = {
                                if (selectedOptionIndex != null) {
                                    isAnswerSubmitted = true
                                    if (selectedOptionIndex == currentQuestion.correctIndex) {
                                        score++
                                    }
                                }
                            },
                            enabled = selectedOptionIndex != null,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Submit Answer")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
