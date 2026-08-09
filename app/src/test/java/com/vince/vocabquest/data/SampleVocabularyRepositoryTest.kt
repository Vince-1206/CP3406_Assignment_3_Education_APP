package com.vince.vocabquest.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SampleVocabularyRepositoryTest {
    private val repository = SampleVocabularyRepository()

    @Test
    fun starterLesson_containsTenQuestions() {
        assertEquals(10, repository.getStarterQuestions().size)
    }

    @Test
    fun everyCorrectAnswer_isIncludedInItsOptions() {
        val questions = repository.getStarterQuestions()

        assertTrue(questions.all { it.correctAnswer in it.options })
    }
}
