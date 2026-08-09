package com.vince.vocabquest.data

import com.vince.vocabquest.data.remote.DefinitionDto
import com.vince.vocabquest.data.remote.DictionaryEntryDto
import com.vince.vocabquest.data.remote.MeaningDto
import com.vince.vocabquest.data.remote.PhoneticDto
import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryRepositoryTest {
    @Test
    fun mapDictionaryEntry_returnsFirstUsableDefinition() {
        val entry = DictionaryEntryDto(
            word = "resilient",
            phonetic = null,
            phonetics = listOf(PhoneticDto(text = "/rɪˈzɪl.i.ənt/")),
            meanings = listOf(
                MeaningDto(
                    partOfSpeech = "adjective",
                    definitions = listOf(
                        DefinitionDto(
                            definition = "Able to recover quickly after difficulty.",
                            example = "The resilient learner tried again.",
                        ),
                    ),
                ),
            ),
        )

        val result = mapDictionaryEntry(entry, requestedWord = "Resilient")

        assertEquals("resilient", result.word)
        assertEquals("/rɪˈzɪl.i.ənt/", result.phonetic)
        assertEquals("adjective", result.partOfSpeech)
        assertEquals("Able to recover quickly after difficulty.", result.definition)
    }

    @Test(expected = NoSuchElementException::class)
    fun mapDictionaryEntry_withoutMeanings_throwsException() {
        mapDictionaryEntry(entry = null, requestedWord = "unknown")
    }
}
