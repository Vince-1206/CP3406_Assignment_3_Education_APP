package com.vince.vocabquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vince.vocabquest.data.DictionaryRepository
import com.vince.vocabquest.data.VocabularyRepository

@Composable
fun LearningRoute(
    repository: VocabularyRepository,
    dictionaryRepository: DictionaryRepository,
    onBackHome: () -> Unit,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearningViewModel = viewModel(
        factory = LearningViewModel.Factory(repository, dictionaryRepository),
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LearningScreen(
        uiState = uiState,
        onPrevious = viewModel::previousWord,
        onNext = viewModel::nextWord,
        onBackHome = onBackHome,
        onStartQuiz = onStartQuiz,
        onRetryDictionary = viewModel::retryDictionary,
        modifier = modifier,
    )
}

@Composable
fun LearningScreen(
    uiState: LearningUiState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onBackHome: () -> Unit,
    onStartQuiz: () -> Unit,
    onRetryDictionary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val word = uiState.currentWord ?: return
    val progress = (uiState.currentIndex + 1).toFloat() / uiState.words.size

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
                text = "Learn vocabulary",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "Read each word before starting the quiz.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            Text("Word ${uiState.currentIndex + 1} of ${uiState.words.size}")
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
                        .padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("Vocabulary word", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = word.definition,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Example",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = word.example,
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Live dictionary", style = MaterialTheme.typography.titleMedium)

                    when {
                        uiState.isDictionaryLoading -> {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                )
                                Text("Checking the online dictionary…")
                            }
                        }

                        uiState.dictionaryDetails != null -> {
                            val details = uiState.dictionaryDetails
                            Text(
                                text = listOfNotNull(
                                    details.partOfSpeech,
                                    details.phonetic,
                                ).joinToString(" • "),
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text("Online definition", style = MaterialTheme.typography.labelLarge)
                            Text(details.definition)
                            details.example?.let { onlineExample ->
                                Text("Online example", style = MaterialTheme.typography.labelLarge)
                                Text(onlineExample, fontStyle = FontStyle.Italic)
                            }
                            Text(
                                text = "Source: Free Dictionary API",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        uiState.isDictionaryUnavailable -> {
                            Text(
                                text = "Online dictionary unavailable. The lesson above still works offline.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            TextButton(onClick = onRetryDictionary) {
                                Text("Try again")
                            }
                        }
                    }
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            ) {
                Text(
                    text = "Memory tip: say the word aloud, explain it in your own words, then read the example once more.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = onPrevious,
                    enabled = !uiState.isFirstWord,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Previous")
                }

                if (uiState.isLastWord) {
                    Button(
                        onClick = onStartQuiz,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Start quiz")
                    }
                } else {
                    Button(
                        onClick = onNext,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Next word")
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}
