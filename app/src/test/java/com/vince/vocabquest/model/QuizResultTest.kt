package com.vince.vocabquest.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class QuizResultTest {
    @Test
    fun percentage_threeCorrectOutOfFour_returnsSeventyFive() {
        val result = QuizResult(correctAnswers = 3, totalQuestions = 4)

        assertEquals(75, result.percentage)
    }

    @Test
    fun percentage_noQuestions_returnsZero() {
        val result = QuizResult(correctAnswers = 0, totalQuestions = 0)

        assertEquals(0, result.percentage)
    }

    @Test
    fun correctAnswersGreaterThanTotal_throwsException() {
        assertThrows(IllegalArgumentException::class.java) {
            QuizResult(correctAnswers = 4, totalQuestions = 3)
        }
    }
}
