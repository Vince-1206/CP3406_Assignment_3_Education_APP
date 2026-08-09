package com.vince.vocabquest.model

data class DictionaryDetails(
    val word: String,
    val phonetic: String?,
    val partOfSpeech: String,
    val definition: String,
    val example: String?,
)
