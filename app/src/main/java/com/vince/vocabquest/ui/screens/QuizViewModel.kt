package com.vince.vocabquest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.VocabularyRepository
import com.vince.vocabquest.model.QuizResult
import com.vince.vocabquest.model.VocabularyQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<VocabularyQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isAnswerSubmitted: Boolean = false,
    val correctAnswers: Int = 0,
    val isComplete: Boolean = false,
) {
    val currentQuestion: VocabularyQuestion?
        get() = questions.getOrNull(currentIndex)

    val result: QuizResult
        get() = QuizResult(correctAnswers, questions.size)
}

class QuizViewModel(
    repository: VocabularyRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        QuizUiState(questions = repository.getStarterQuestions()),
    )
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun selectAnswer(answer: String) {
        if (!_uiState.value.isAnswerSubmitted) {
            _uiState.update { it.copy(selectedAnswer = answer) }
        }
    }

    fun submitAnswer() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        val answer = state.selectedAnswer ?: return
        if (state.isAnswerSubmitted) return

        _uiState.update {
            it.copy(
                isAnswerSubmitted = true,
                correctAnswers = it.correctAnswers + if (answer == question.correctAnswer) 1 else 0,
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        if (!state.isAnswerSubmitted || state.isComplete) return

        if (state.currentIndex == state.questions.lastIndex) {
            _uiState.update { it.copy(isComplete = true) }
            viewModelScope.launch {
                progressRepository.saveQuizResult(state.result)
            }
        } else {
            _uiState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedAnswer = null,
                    isAnswerSubmitted = false,
                )
            }
        }
    }

    fun restartQuiz() {
        _uiState.update {
            QuizUiState(questions = it.questions)
        }
    }

    class Factory(
        private val repository: VocabularyRepository,
        private val progressRepository: ProgressRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
                return QuizViewModel(repository, progressRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
