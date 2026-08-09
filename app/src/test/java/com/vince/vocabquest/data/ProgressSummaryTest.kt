package com.vince.vocabquest.data

import com.vince.vocabquest.model.QuizResult
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressSummaryTest {
    @Test
    fun twoQuizResults_calculatesRealProgress() {
        val summary = calculateProgressSummary(
            listOf(
                QuizResult(correctAnswers = 8, totalQuestions = 10),
                QuizResult(correctAnswers = 6, totalQuestions = 10),
            ),
        )

        assertEquals(70, summary.averageScore)
        assertEquals(2, summary.quizzesFinished)
        assertEquals(80, summary.latestScore)
        assertEquals(80, summary.bestScore)
        assertEquals(20, summary.questionsCompleted)
    }

    @Test
    fun noQuizResults_returnsZeroProgress() {
        assertEquals(ProgressSummary(), calculateProgressSummary(emptyList()))
    }
}
