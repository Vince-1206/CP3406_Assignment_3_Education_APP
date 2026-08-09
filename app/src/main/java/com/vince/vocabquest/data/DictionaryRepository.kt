package com.vince.vocabquest.data

import com.vince.vocabquest.data.remote.DictionaryApiService
import com.vince.vocabquest.data.remote.DictionaryEntryDto
import com.vince.vocabquest.model.DictionaryDetails

interface DictionaryRepository {
    suspend fun lookUpWord(word: String): DictionaryDetails
}

class NetworkDictionaryRepository(
    private val apiService: DictionaryApiService,
) : DictionaryRepository {
    private val cache = mutableMapOf<String, DictionaryDetails>()

    override suspend fun lookUpWord(word: String): DictionaryDetails {
        val normalizedWord = word.trim().lowercase()
        cache[normalizedWord]?.let { return it }

        return mapDictionaryEntry(
            entry = apiService.getWord(normalizedWord).firstOrNull(),
            requestedWord = normalizedWord,
        ).also { details ->
            cache[normalizedWord] = details
        }
    }
}

fun mapDictionaryEntry(
    entry: DictionaryEntryDto?,
    requestedWord: String,
): DictionaryDetails {
    val meaning = entry?.meanings
        ?.firstOrNull { candidate ->
            candidate.definitions?.any { !it.definition.isNullOrBlank() } == true
        }
        ?: throw NoSuchElementException("No dictionary meaning found for $requestedWord")

    val definition = meaning.definitions
        ?.firstOrNull { !it.definition.isNullOrBlank() }
        ?: throw NoSuchElementException("No dictionary definition found for $requestedWord")

    return DictionaryDetails(
        word = entry.word?.takeIf { it.isNotBlank() } ?: requestedWord,
        phonetic = entry.phonetic?.takeIf { it.isNotBlank() }
            ?: entry.phonetics?.firstNotNullOfOrNull { phonetic ->
                phonetic.text?.takeIf { it.isNotBlank() }
            },
        partOfSpeech = meaning.partOfSpeech?.takeIf { it.isNotBlank() } ?: "word",
        definition = requireNotNull(definition.definition),
        example = definition.example?.takeIf { it.isNotBlank() },
    )
}
