package com.vince.vocabquest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

const val LEARNING_ROUTE = "learning"
const val QUIZ_ROUTE = "quiz"

enum class VocabQuestDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Home(route = "home", label = "Home", icon = Icons.Default.Home),
    Statistics(route = "statistics", label = "Progress", icon = Icons.Default.BarChart),
    Settings(route = "settings", label = "Settings", icon = Icons.Default.Settings),
}
