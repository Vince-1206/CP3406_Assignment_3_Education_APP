package com.vince.vocabquest.model

data class VocabularyQuestion(
    val id: Int,
    val word: String,
    val definition: String,
    val example: String,
    val options: List<String>,
    val correctAnswer: String,
) {
    init {
        require(word.isNotBlank()) { "Word cannot be blank." }
        require(options.size >= 2) { "A question needs at least two answer options." }
        require(correctAnswer in options) { "The correct answer must be one of the options." }
    }
}
