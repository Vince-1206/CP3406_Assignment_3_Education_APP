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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.VocabularyRepository

@Composable
fun ActivityRoute(
    repository: VocabularyRepository,
    progressRepository: ProgressRepository,
    onBackHome: () -> Unit,
    onViewProgress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(
        factory = QuizViewModel.Factory(repository, progressRepository),
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ActivityScreen(
        uiState = uiState,
        onAnswerSelected = viewModel::selectAnswer,
        onSubmit = viewModel::submitAnswer,
        onNext = viewModel::nextQuestion,
        onRestart = viewModel::restartQuiz,
        onBackHome = onBackHome,
        onViewProgress = onViewProgress,
        modifier = modifier,
    )
}

@Composable
fun ActivityScreen(
    uiState: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onBackHome: () -> Unit,
    onViewProgress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isComplete) {
        QuizCompleteScreen(
            correctAnswers = uiState.result.correctAnswers,
            totalQuestions = uiState.result.totalQuestions,
            percentage = uiState.result.percentage,
            onRestart = onRestart,
            onBackHome = onBackHome,
            onViewProgress = onViewProgress,
            modifier = modifier,
        )
        return
    }

    val question = uiState.currentQuestion ?: return
    val progress = (uiState.currentIndex + 1).toFloat() / uiState.questions.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            TextButton(onClick = onBackHome) {
                Text("← Back to Home")
            }
        }
        item {
            Text(
                text = "Vocabulary quiz",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "Choose the best meaning. Definitions stay hidden until you answer.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            Text("Question ${uiState.currentIndex + 1} of ${uiState.questions.size}")
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
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
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("What does this word mean?", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = question.word,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
        item {
            Text(
                text = "Choose the correct meaning",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        item {
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                question.options.forEach { option ->
                    AnswerOption(
                        text = option,
                        selected = uiState.selectedAnswer == option,
                        enabled = !uiState.isAnswerSubmitted,
                        onClick = { onAnswerSelected(option) },
                    )
                }
            }
        }
        if (uiState.isAnswerSubmitted) {
            item {
                val isCorrect = uiState.selectedAnswer == question.correctAnswer
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) {
                            MaterialTheme.colorScheme.tertiaryContainer
                        } else {
                            MaterialTheme.colorScheme.errorContainer
                        },
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            text = if (isCorrect) {
                                "Correct! Great work."
                            } else {
                                "Not quite. The answer is: ${question.correctAnswer}"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = "Meaning: ${question.definition}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        item {
            Button(
                onClick = if (uiState.isAnswerSubmitted) onNext else onSubmit,
                enabled = uiState.selectedAnswer != null,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    if (uiState.isAnswerSubmitted) {
                        if (uiState.currentIndex == uiState.questions.lastIndex) "See results" else "Next question"
                    } else {
                        "Check answer"
                    },
                )
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled,
            )
            Text(text = text, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuizCompleteScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    percentage: Int,
    onRestart: () -> Unit,
    onBackHome: () -> Unit,
    onViewProgress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Quiz complete!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "$correctAnswers of $totalQuestions answers correct",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onViewProgress,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("View Progress")
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = onBackHome,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Back to Home")
        }
        TextButton(onClick = onRestart) {
            Text("Try quiz again")
        }
    }
}
