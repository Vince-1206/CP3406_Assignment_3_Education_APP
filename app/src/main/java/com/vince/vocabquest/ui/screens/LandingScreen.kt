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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.ProgressSummary

@Composable
fun LandingRoute(
    progressRepository: ProgressRepository,
    onStartLearning: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(progressRepository)),
) {
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    LandingScreen(
        progress = progress,
        onStartLearning = onStartLearning,
        modifier = modifier,
    )
}

@Composable
fun LandingScreen(
    progress: ProgressSummary = ProgressSummary(),
    onStartLearning: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "VocabQuest",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Build stronger English, one word at a time.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
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
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = "Today's learning goal",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                    Text(
                        text = "Learn 10 academic words, then continue directly to a 10-question quiz.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Starter lesson • 10 words",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Button(
                        onClick = onStartLearning,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Start learning")
                    }
                }
            }
        }
        item {
            Text(
                text = "Your overview",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OverviewCard(
                    icon = { Icon(Icons.Default.Quiz, contentDescription = null) },
                    value = progress.quizzesFinished.toString(),
                    label = "Quizzes finished",
                    modifier = Modifier.weight(1f),
                )
                OverviewCard(
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                    value = progress.questionsCompleted.toString(),
                    label = "Questions completed",
                    modifier = Modifier.weight(1f),
                )
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = "Privacy by design",
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "VocabQuest does not ask for your name, email, or location. Quiz progress is stored only on this device.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}

@Composable
private fun OverviewCard(
    icon: @Composable () -> Unit,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            icon()
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
