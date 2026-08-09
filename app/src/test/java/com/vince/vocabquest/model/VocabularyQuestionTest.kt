package com.vince.vocabquest.model

import org.junit.Assert.assertThrows
import org.junit.Test

class VocabularyQuestionTest {
    @Test
    fun correctAnswerMissingFromOptions_throwsException() {
        assertThrows(IllegalArgumentException::class.java) {
            VocabularyQuestion(
                id = 1,
                word = "Resilient",
                definition = "Able to recover quickly.",
                example = "She remained resilient.",
                options = listOf("Quiet", "Fast"),
                correctAnswer = "Able to recover quickly",
            )
        }
    }
}
