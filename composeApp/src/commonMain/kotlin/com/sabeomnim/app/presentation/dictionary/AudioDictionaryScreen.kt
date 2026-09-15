package com.sabeomnim.app.presentation.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.audio.rememberAudioService
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.designsystem.TaegeukRed
import com.sabeomnim.app.core.i18n.AppLanguage
import com.sabeomnim.app.core.i18n.AppStrings
import com.sabeomnim.app.core.i18n.LocalAppLanguage
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.TermCategory
import com.sabeomnim.app.data.repository.TerminologyRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioDictionaryScreen() {
    val lang = LocalAppLanguage.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<TermCategory?>(null) }
    var selectedBeltFilter by remember { mutableStateOf<BeltRank?>(null) }
    var isSlowMode by remember { mutableStateOf(false) }
    var currentlyPlayingId by remember { mutableStateOf<String?>(null) }

    val audioService = rememberAudioService()

    val allTerms = remember { TerminologyRepository.getAllTerms() }

    val filteredTerms = remember(searchQuery, selectedCategory, selectedBeltFilter) {
        var list = if (searchQuery.isNotBlank()) {
            TerminologyRepository.searchTerms(searchQuery)
        } else {
            allTerms
        }

        selectedCategory?.let { cat ->
            list = list.filter { it.category == cat }
        }

        selectedBeltFilter?.let { belt ->
            list = list.filter { it.beltRank == belt }
        }

        list
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(AppStrings.tabGlossary(lang), fontWeight = FontWeight.Bold, color = TaegeukRed, fontSize = 20.sp)
                },
                actions = {
                    // Slow Audio Toggle
                    FilterChip(
                        selected = isSlowMode,
                        onClick = { isSlowMode = !isSlowMode },
                        label = {
                            Text(
                                if (isSlowMode) AppStrings.slowAudio(lang) else AppStrings.normalSpeed(lang),
                                fontSize = 12.sp,
                                fontWeight = if (isSlowMode) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Input Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(AppStrings.searchPlaceholder(lang)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Category Chips Row (10 Complete Blue Dragon Categories)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    val count = allTerms.size
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text("${AppStrings.allCategories(lang)} ($count)") }
                    )
                }
                items(TermCategory.entries) { cat ->
                    val isSelected = selectedCategory == cat
                    val count = allTerms.count { it.category == cat }
                    val catTitle = if (lang == AppLanguage.DANISH) cat.titleDanish else cat.title
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = if (isSelected) null else cat },
                        label = { Text("$catTitle ($count)") }
                    )
                }
            }

            // Results count and pronunciation tip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredTerms.size} ${AppStrings.termsCount(lang, allTerms.size)}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = AppStrings.tapToHearAudio(lang),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TaegeukBlue
                )
            }

            // Terminology List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredTerms, key = { it.id }) { term ->
                    val isPlaying = currentlyPlayingId == term.id

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                currentlyPlayingId = term.id
                                audioService.speak(term.hangul, isSlow = isSlowMode)
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isPlaying) TaegeukBlue.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                        ),
                        border = if (isPlaying) androidx.compose.foundation.BorderStroke(1.5.dp, TaegeukBlue) else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = term.romanized,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isPlaying) TaegeukBlue else MaterialTheme.colorScheme.onSurface
                                    )
                                    val catBadge = if (lang == AppLanguage.DANISH) {
                                        term.category.titleDanish.substringBefore(" (").substringBefore(" &")
                                    } else {
                                        term.category.title.substringBefore(" (").substringBefore(" &")
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = catBadge,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                if (term.phoneticSpelling != null && term.phoneticSpelling != term.romanized) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Alt: ${term.phoneticSpelling}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))
                                if (lang == AppLanguage.DANISH && term.danish != null) {
                                    Text(
                                        text = term.danish!!,
                                        fontSize = 14.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TaegeukBlue
                                    )
                                    Spacer(modifier = Modifier.height(1.dp))
                                    Text(
                                        text = "🇬🇧 ${term.english}",
                                        fontSize = 12.5.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    Text(
                                        text = term.english,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (term.danish != null) {
                                        Spacer(modifier = Modifier.height(1.dp))
                                        Text(
                                            text = "🇩🇰 ${term.danish}",
                                            fontSize = 12.5.sp,
                                            color = TaegeukBlue
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                val explanation = if (lang == AppLanguage.DANISH && term.danishExplanation != null) {
                                    term.danishExplanation
                                } else {
                                    term.explanation
                                }
                                Text(
                                    text = explanation,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // One-tap Audio Speaker Button
                            FilledIconButton(
                                onClick = {
                                    currentlyPlayingId = term.id
                                    audioService.speak(term.hangul, isSlow = isSlowMode)
                                },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = if (isPlaying) TaegeukRed else TaegeukBlue
                                ),
                                modifier = Modifier.size(46.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Pronounce",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
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
}
