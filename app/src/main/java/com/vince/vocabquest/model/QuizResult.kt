package com.vince.vocabquest.model

data class QuizResult(
    val correctAnswers: Int,
    val totalQuestions: Int,
) {
    init {
        require(totalQuestions >= 0) { "Total questions cannot be negative." }
        require(correctAnswers in 0..totalQuestions) {
            "Correct answers must be between zero and total questions."
        }
    }

    val percentage: Int
        get() = if (totalQuestions == 0) 0 else (correctAnswers * 100) / totalQuestions
}
