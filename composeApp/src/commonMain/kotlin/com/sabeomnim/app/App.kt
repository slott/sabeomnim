package com.sabeomnim.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sabeomnim.app.core.designsystem.SabeomnimTheme
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.core.i18n.AppLanguage
import com.sabeomnim.app.core.i18n.AppStrings
import com.sabeomnim.app.core.i18n.LocalAppLanguage
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.presentation.dashboard.BeltDashboardScreen
import com.sabeomnim.app.presentation.dictionary.AudioDictionaryScreen
import com.sabeomnim.app.presentation.poomsae.PoomsaePlayerScreen
import com.sabeomnim.app.presentation.quiz.QuizScreen

import androidx.compose.ui.text.font.FontWeight
import com.sabeomnim.app.core.designsystem.ThemeMode
import com.sabeomnim.app.presentation.settings.SettingsScreen

enum class AppTab(val title: String, val icon: ImageVector) {
    BELTS("Curriculum", Icons.Default.SportsMartialArts),
    POOMSAE("Taegeuks", Icons.Default.PlayCircle),
    AUDIO_DICT("Glossary", Icons.AutoMirrored.Filled.VolumeUp),
    QUIZ("Quiz", Icons.Default.Quiz);

    fun localizedTitle(lang: AppLanguage): String = when (this) {
        BELTS -> AppStrings.tabCurriculum(lang)
        POOMSAE -> AppStrings.tabPoomsae(lang)
        AUDIO_DICT -> AppStrings.tabGlossary(lang)
        QUIZ -> AppStrings.tabQuiz(lang)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SabeomnimTopBar(
    currentTab: AppTab,
    lang: AppLanguage,
    onOpenSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = currentTab.localizedTitle(lang),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = onOpenSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = AppStrings.settingsTitle(lang),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun App() {
    var currentLanguage by rememberSaveable { mutableStateOf(AppLanguage.DANISH) }
    var currentThemeMode by rememberSaveable { mutableStateOf(ThemeMode.DARK) }
    var isShowingSettings by rememberSaveable { mutableStateOf(false) }

    CompositionLocalProvider(LocalAppLanguage provides currentLanguage) {
        SabeomnimTheme(themeMode = currentThemeMode) {
            if (isShowingSettings) {
                SettingsScreen(
                    currentThemeMode = currentThemeMode,
                    onThemeModeChange = { currentThemeMode = it },
                    onLanguageChange = { currentLanguage = it },
                    onClose = { isShowingSettings = false }
                )
            } else {
                var currentTab by remember { mutableStateOf(AppTab.BELTS) }
                var currentSelectedBelt by remember { mutableStateOf(BeltRank.WHITE) }
                var activePoomsaeId by remember { mutableStateOf("taegeuk_1") }

                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val isLandscape = maxWidth > maxHeight

                    if (isLandscape) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            NavigationRail(
                                modifier = Modifier.fillMaxHeight(),
                                containerColor = MaterialTheme.colorScheme.surface
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                AppTab.entries.forEach { tab ->
                                    val isSelected = currentTab == tab
                                    NavigationRailItem(
                                        selected = isSelected,
                                        onClick = { currentTab = tab },
                                        icon = {
                                            Icon(
                                                imageVector = tab.icon,
                                                contentDescription = tab.localizedTitle(currentLanguage)
                                            )
                                        },
                                        label = { Text(tab.localizedTitle(currentLanguage)) },
                                        colors = NavigationRailItemDefaults.colors(
                                            indicatorColor = TaegeukBlue.copy(alpha = 0.15f),
                                            selectedIconColor = TaegeukBlue,
                                            selectedTextColor = TaegeukBlue
                                        )
                                    )
                                }
                            }

                            Scaffold(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                topBar = {
                                    SabeomnimTopBar(
                                        currentTab = currentTab,
                                        lang = currentLanguage,
                                        onOpenSettings = { isShowingSettings = true }
                                    )
                                }
                            ) { innerPadding ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                ) {
                                    AppScreenContent(
                                        currentTab = currentTab,
                                        currentSelectedBelt = currentSelectedBelt,
                                        activePoomsaeId = activePoomsaeId,
                                        onBeltSelected = { currentSelectedBelt = it },
                                        onOpenPoomsae = { id ->
                                            activePoomsaeId = id
                                            currentTab = AppTab.POOMSAE
                                        },
                                        onOpenQuiz = { belt ->
                                            currentSelectedBelt = belt
                                            currentTab = AppTab.QUIZ
                                        },
                                        onOpenDictionary = {
                                            currentTab = AppTab.AUDIO_DICT
                                        },
                                        onOpenSettings = {
                                            isShowingSettings = true
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        Scaffold(
                            topBar = {
                                SabeomnimTopBar(
                                    currentTab = currentTab,
                                    lang = currentLanguage,
                                    onOpenSettings = { isShowingSettings = true }
                                )
                            },
                            bottomBar = {
                                NavigationBar {
                                    AppTab.entries.forEach { tab ->
                                        val isSelected = currentTab == tab
                                        NavigationBarItem(
                                            selected = isSelected,
                                            onClick = { currentTab = tab },
                                            icon = {
                                                Icon(
                                                    imageVector = tab.icon,
                                                    contentDescription = tab.localizedTitle(currentLanguage)
                                                )
                                            },
                                            label = { Text(tab.localizedTitle(currentLanguage)) },
                                            colors = NavigationBarItemDefaults.colors(
                                                indicatorColor = TaegeukBlue.copy(alpha = 0.15f),
                                                selectedIconColor = TaegeukBlue,
                                                selectedTextColor = TaegeukBlue
                                            )
                                        )
                                    }
                                }
                            }
                        ) { innerPadding ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                AppScreenContent(
                                    currentTab = currentTab,
                                    currentSelectedBelt = currentSelectedBelt,
                                    activePoomsaeId = activePoomsaeId,
                                    onBeltSelected = { currentSelectedBelt = it },
                                    onOpenPoomsae = { id ->
                                        activePoomsaeId = id
                                        currentTab = AppTab.POOMSAE
                                    },
                                    onOpenQuiz = { belt ->
                                        currentSelectedBelt = belt
                                        currentTab = AppTab.QUIZ
                                    },
                                    onOpenDictionary = {
                                        currentTab = AppTab.AUDIO_DICT
                                    },
                                    onOpenSettings = {
                                        isShowingSettings = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AppScreenContent(
    currentTab: AppTab,
    currentSelectedBelt: BeltRank,
    activePoomsaeId: String,
    onBeltSelected: (BeltRank) -> Unit,
    onOpenPoomsae: (String) -> Unit,
    onOpenQuiz: (BeltRank) -> Unit,
    onOpenDictionary: () -> Unit,
    onOpenSettings: () -> Unit
) {
    when (currentTab) {
        AppTab.BELTS -> BeltDashboardScreen(
            selectedBelt = currentSelectedBelt,
            onBeltSelected = onBeltSelected,
            onOpenPoomsae = onOpenPoomsae,
            onOpenQuiz = onOpenQuiz,
            onOpenDictionary = onOpenDictionary,
            onOpenSettings = onOpenSettings
        )
        AppTab.POOMSAE -> PoomsaePlayerScreen(
            initialPoomsaeId = activePoomsaeId,
            onOpenSettings = onOpenSettings
        )
        AppTab.AUDIO_DICT -> AudioDictionaryScreen(
            onOpenSettings = onOpenSettings
        )
        AppTab.QUIZ -> QuizScreen(
            initialBelt = currentSelectedBelt,
            onOpenSettings = onOpenSettings
        )
    }
}
