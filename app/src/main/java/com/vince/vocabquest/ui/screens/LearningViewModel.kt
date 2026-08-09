package com.vince.vocabquest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vince.vocabquest.data.DictionaryRepository
import com.vince.vocabquest.data.VocabularyRepository
import com.vince.vocabquest.model.DictionaryDetails
import com.vince.vocabquest.model.VocabularyQuestion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LearningUiState(
    val words: List<VocabularyQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val dictionaryDetails: DictionaryDetails? = null,
    val isDictionaryLoading: Boolean = false,
    val isDictionaryUnavailable: Boolean = false,
) {
    val currentWord: VocabularyQuestion?
        get() = words.getOrNull(currentIndex)

    val isFirstWord: Boolean
        get() = currentIndex == 0

    val isLastWord: Boolean
        get() = words.isNotEmpty() && currentIndex == words.lastIndex
}

class LearningViewModel(
    repository: VocabularyRepository,
    private val dictionaryRepository: DictionaryRepository,
) : ViewModel() {
    private var lookupJob: Job? = null

    private val _uiState = MutableStateFlow(
        LearningUiState(words = repository.getStarterQuestions()),
    )
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    init {
        loadCurrentWord()
    }

    fun previousWord() {
        _uiState.update { state ->
            state.copy(currentIndex = (state.currentIndex - 1).coerceAtLeast(0))
        }
        loadCurrentWord()
    }

    fun nextWord() {
        _uiState.update { state ->
            if (state.words.isEmpty()) {
                state
            } else {
                state.copy(currentIndex = (state.currentIndex + 1).coerceAtMost(state.words.lastIndex))
            }
        }
        loadCurrentWord()
    }

    fun retryDictionary() {
        loadCurrentWord()
    }

    private fun loadCurrentWord() {
        val requestedWord = _uiState.value.currentWord?.word ?: return
        lookupJob?.cancel()
        _uiState.update {
            it.copy(
                dictionaryDetails = null,
                isDictionaryLoading = true,
                isDictionaryUnavailable = false,
            )
        }

        lookupJob = viewModelScope.launch {
            try {
                val details = dictionaryRepository.lookUpWord(requestedWord.lowercase())
                if (_uiState.value.currentWord?.word == requestedWord) {
                    _uiState.update {
                        it.copy(
                            dictionaryDetails = details,
                            isDictionaryLoading = false,
                        )
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                if (_uiState.value.currentWord?.word == requestedWord) {
                    _uiState.update {
                        it.copy(
                            dictionaryDetails = null,
                            isDictionaryLoading = false,
                            isDictionaryUnavailable = true,
                        )
                    }
                }
            }
        }
    }

    class Factory(
        private val repository: VocabularyRepository,
        private val dictionaryRepository: DictionaryRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LearningViewModel::class.java)) {
                return LearningViewModel(repository, dictionaryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
