package com.sabeomnim.app.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.withFrameNanos
import kotlin.math.ceil
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.audio.rememberAudioService
import com.sabeomnim.app.core.ui.belt.UnfoldingBeltView
import com.sabeomnim.app.core.ui.confetti.ConfettiHost
import com.sabeomnim.app.core.ui.confetti.rememberConfettiState
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
    var autoNextProgress by remember { mutableStateOf(0f) }
    val autoNextDurationMs = 2800L
    val listState = rememberLazyListState()

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
        autoNextProgress = 0f
    }

    val confettiState = rememberConfettiState()
    val passScore = remember(questions) { (questions.size * 0.70).toInt().coerceAtLeast(1) }
    val passed = score >= passScore

    LaunchedEffect(isQuizCompleted, passed) {
        if (isQuizCompleted && passed) {
            confettiState.spawnCelebration(180)
        }
    }

    // Auto-advance progress timer when an answer is submitted
    LaunchedEffect(isAnswerSubmitted, currentQuestionIndex) {
        if (isAnswerSubmitted && !isQuizCompleted) {
            val startNanos = withFrameNanos { it }
            val durationNanos = autoNextDurationMs * 1_000_000L
            while (true) {
                val nowNanos = withFrameNanos { it }
                val elapsed = nowNanos - startNanos
                val fraction = (elapsed.toFloat() / durationNanos).coerceIn(0f, 1f)
                autoNextProgress = fraction
                if (fraction >= 1f) {
                    if (currentQuestionIndex + 1 < questions.size) {
                        currentQuestionIndex++
                        selectedOptionIndex = null
                        isAnswerSubmitted = false
                    } else {
                        isQuizCompleted = true
                    }
                    break
                }
            }
        } else {
            autoNextProgress = 0f
        }
    }

    // Smoothly scroll down so explanation and auto-advancing Next button are revealed
    LaunchedEffect(isAnswerSubmitted) {
        if (isAnswerSubmitted) {
            listState.animateScrollToItem(index = (currentQuestion?.options?.size ?: 4) + 2)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Grading Theory Exam", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TaegeukRed)
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
            state = listState,
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
                                com.sabeomnim.app.core.ui.belt.BeltMiniIcon(belt = belt)
                            }
                        )
                    }
                }
            }

            if (isQuizCompleted) {
                // Completed Summary Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (passed) Color(0xFF2E7D32).copy(alpha = 0.20f) else TaegeukRed.copy(alpha = 0.20f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (passed) "🎉 PROMOTION READY!" else "📚 NEEDS STUDY (Retake)",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (passed) Color(0xFF2E7D32) else TaegeukRed
                            )

                            if (passed) {
                                Spacer(modifier = Modifier.height(14.dp))
                                // Celebration Unfolding Belt for the earned rank
                                UnfoldingBeltView(
                                    belt = selectedBelt,
                                    boxWidth = 96.dp,
                                    maxBeltLength = 150.dp
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "Your Score: $score / ${questions.size}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            val percent = if (questions.isNotEmpty()) (score * 100) / questions.size else 0
                            Text(
                                text = "$percent% Mastery for ${selectedBelt.gradeText}" + if (!passed) " (70% required to pass)" else "",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { resetQuiz() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (passed) Color(0xFF2E7D32) else TaegeukRed
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Retake", maxLines = 1)
                                }

                                if (passed) {
                                    FilledTonalButton(
                                        onClick = { confettiState.spawnCelebration(180) },
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                                    ) {
                                        Text("🎊 Confetti", maxLines = 1)
                                    }
                                }
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
                        !isAnswerSubmitted && isSelected -> TaegeukBlue.copy(alpha = 0.16f)
                        isAnswerSubmitted && isCorrectOption -> Color(0xFF2E7D32).copy(alpha = 0.25f)
                        isAnswerSubmitted && isSelected && !isCorrectOption -> TaegeukRed.copy(alpha = 0.25f)
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
                        val isLastQuestion = currentQuestionIndex + 1 >= questions.size
                        val secondsLeft = ceil((1f - autoNextProgress) * (autoNextDurationMs / 1000f)).toInt().coerceAtLeast(1)

                        // Next button with animated progress bar indicating auto-advance
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable {
                                    if (currentQuestionIndex + 1 < questions.size) {
                                        currentQuestionIndex++
                                        selectedOptionIndex = null
                                        isAnswerSubmitted = false
                                    } else {
                                        isQuizCompleted = true
                                    }
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // 1. Shaded progress bar fill sweeping across the button
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(autoNextProgress)
                                    .background(Color.White.copy(alpha = 0.22f))
                            )

                            // 2. High-contrast accent progress bar across the bottom
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth(autoNextProgress)
                                    .height(4.dp)
                                    .background(Color.White.copy(alpha = 0.95f))
                            )

                            // 3. Button content: Label + Auto-next countdown indicator + Arrow
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isLastQuestion) "See Results" else "Next Question",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.Black.copy(alpha = 0.25f)
                                ) {
                                    Text(
                                        text = "${secondsLeft}s",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
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

    // Celebratory Confetti Particle System overlay
    ConfettiHost(
        state = confettiState,
        modifier = Modifier.fillMaxSize()
    )
}
}
