package com.vince.vocabquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.ProgressSummary

@Composable
fun StatisticsRoute(
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = viewModel(
        factory = StatisticsViewModel.Factory(progressRepository),
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    StatisticsScreen(uiState = uiState, modifier = modifier)
}

@Composable
fun StatisticsScreen(
    uiState: ProgressSummary,
    modifier: Modifier = Modifier,
) {
    val goal = 20
    val completedForGoal = uiState.questionsCompleted.coerceAtMost(goal)
    val remaining = (goal - completedForGoal).coerceAtLeast(0)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            Text(
                text = "Your progress",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text("Practice goal", style = MaterialTheme.typography.titleLarge)
                    Text("$completedForGoal of $goal quiz questions completed")
                    LinearProgressIndicator(
                        progress = { completedForGoal.toFloat() / goal },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = when {
                            completedForGoal == 0 -> "Complete your first quiz to start your progress."
                            remaining == 0 -> "Practice goal reached — great work!"
                            else -> "Keep going — $remaining questions remain."
                        },
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatisticCard("${uiState.averageScore}%", "Average score", Modifier.weight(1f))
                StatisticCard(uiState.quizzesFinished.toString(), "Quizzes finished", Modifier.weight(1f))
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatisticCard("${uiState.latestScore}%", "Latest score", Modifier.weight(1f))
                StatisticCard("${uiState.bestScore}%", "Best score", Modifier.weight(1f))
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("About these statistics", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "These statistics come from quiz results stored locally with Room. No account or personal information is needed.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}

@Composable
private fun StatisticCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
