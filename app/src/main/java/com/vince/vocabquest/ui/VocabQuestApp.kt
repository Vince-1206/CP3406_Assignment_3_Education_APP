package com.vince.vocabquest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vince.vocabquest.data.DictionaryRepository
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.VocabularyRepository
import com.vince.vocabquest.ui.navigation.LEARNING_ROUTE
import com.vince.vocabquest.ui.navigation.QUIZ_ROUTE
import com.vince.vocabquest.ui.navigation.VocabQuestDestination
import com.vince.vocabquest.ui.screens.ActivityRoute
import com.vince.vocabquest.ui.screens.LandingRoute
import com.vince.vocabquest.ui.screens.LearningRoute
import com.vince.vocabquest.ui.screens.SettingsRoute
import com.vince.vocabquest.ui.screens.StatisticsRoute

@Composable
fun VocabQuestApp(
    vocabularyRepository: VocabularyRepository,
    progressRepository: ProgressRepository,
    dictionaryRepository: DictionaryRepository,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val destinations = VocabQuestDestination.entries
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(VocabQuestDestination.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label,
                            )
                        },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VocabQuestDestination.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(VocabQuestDestination.Home.route) {
                LandingRoute(
                    progressRepository = progressRepository,
                    onStartLearning = {
                        navController.navigate(LEARNING_ROUTE)
                    },
                )
            }
            composable(LEARNING_ROUTE) {
                LearningRoute(
                    repository = vocabularyRepository,
                    dictionaryRepository = dictionaryRepository,
                    onBackHome = {
                        navController.navigate(VocabQuestDestination.Home.route) {
                            popUpTo(VocabQuestDestination.Home.route)
                            launchSingleTop = true
                        }
                    },
                    onStartQuiz = {
                        navController.navigate(QUIZ_ROUTE)
                    },
                )
            }
            composable(QUIZ_ROUTE) {
                ActivityRoute(
                    repository = vocabularyRepository,
                    progressRepository = progressRepository,
                    onBackHome = {
                        navController.navigate(VocabQuestDestination.Home.route) {
                            popUpTo(VocabQuestDestination.Home.route)
                            launchSingleTop = true
                        }
                    },
                    onViewProgress = {
                        navController.navigate(VocabQuestDestination.Statistics.route)
                    },
                )
            }
            composable(VocabQuestDestination.Statistics.route) {
                StatisticsRoute(progressRepository = progressRepository)
            }
            composable(VocabQuestDestination.Settings.route) {
                SettingsRoute()
            }
        }
    }
}
