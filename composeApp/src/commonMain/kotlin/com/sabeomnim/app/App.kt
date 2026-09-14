package com.sabeomnim.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sabeomnim.app.core.designsystem.SabeomnimTheme
import com.sabeomnim.app.core.designsystem.TaegeukBlue
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.presentation.dashboard.BeltDashboardScreen
import com.sabeomnim.app.presentation.dictionary.AudioDictionaryScreen
import com.sabeomnim.app.presentation.poomsae.PoomsaePlayerScreen
import com.sabeomnim.app.presentation.quiz.QuizScreen

enum class AppTab(val title: String, val icon: ImageVector) {
    BELTS("Curriculum", Icons.Default.SportsMartialArts),
    POOMSAE("Taegeuks", Icons.Default.PlayCircle),
    AUDIO_DICT("Korean Audio", Icons.Default.VolumeUp),
    QUIZ("Belt Quiz", Icons.Default.Quiz)
}

@Composable
fun App() {
    SabeomnimTheme {
        var currentTab by remember { mutableStateOf(AppTab.BELTS) }
        var currentSelectedBelt by remember { mutableStateOf(BeltRank.WHITE) }
        var activePoomsaeId by remember { mutableStateOf("taegeuk_1") }

        Scaffold(
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
                                    contentDescription = tab.title
                                )
                            },
                            label = { Text(tab.title) },
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
                when (currentTab) {
                    AppTab.BELTS -> BeltDashboardScreen(
                        selectedBelt = currentSelectedBelt,
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
                        }
                    )
                    AppTab.POOMSAE -> PoomsaePlayerScreen(
                        initialPoomsaeId = activePoomsaeId
                    )
                    AppTab.AUDIO_DICT -> AudioDictionaryScreen()
                    AppTab.QUIZ -> QuizScreen(
                        initialBelt = currentSelectedBelt
                    )
                }
            }
        }
    }
}
