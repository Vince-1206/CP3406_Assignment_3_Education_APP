package com.vince.vocabquest.data

import com.vince.vocabquest.model.VocabularyQuestion

interface VocabularyRepository {
    fun getStarterQuestions(): List<VocabularyQuestion>
}

class SampleVocabularyRepository : VocabularyRepository {
    override fun getStarterQuestions(): List<VocabularyQuestion> = listOf(
        VocabularyQuestion(
            id = 1,
            word = "Resilient",
            definition = "Able to recover quickly from difficulty.",
            example = "The resilient student tried again after making a mistake.",
            options = listOf(
                "Able to recover quickly",
                "Easy to confuse",
                "Unwilling to change",
                "Very expensive",
            ),
            correctAnswer = "Able to recover quickly",
        ),
        VocabularyQuestion(
            id = 2,
            word = "Analyse",
            definition = "To examine something carefully to understand it.",
            example = "We analyse the evidence before reaching a conclusion.",
            options = listOf(
                "To memorise without thinking",
                "To examine carefully",
                "To remove completely",
                "To describe briefly",
            ),
            correctAnswer = "To examine carefully",
        ),
        VocabularyQuestion(
            id = 3,
            word = "Relevant",
            definition = "Closely connected to the topic being discussed.",
            example = "Use only relevant information in your answer.",
            options = listOf(
                "Connected to the topic",
                "Impossible to explain",
                "From a different language",
                "Written a long time ago",
            ),
            correctAnswer = "Connected to the topic",
        ),
        VocabularyQuestion(
            id = 4,
            word = "Significant",
            definition = "Important enough to deserve attention.",
            example = "The experiment produced a significant improvement in the results.",
            options = listOf(
                "Important and noticeable",
                "Small and unimportant",
                "Difficult to remember",
                "Unrelated to the topic",
            ),
            correctAnswer = "Important and noticeable",
        ),
        VocabularyQuestion(
            id = 5,
            word = "Evidence",
            definition = "Information or facts that support an idea or conclusion.",
            example = "The student used evidence from the article to support her argument.",
            options = listOf(
                "Facts that support an idea",
                "A personal greeting",
                "An unfinished question",
                "A type of calculation",
            ),
            correctAnswer = "Facts that support an idea",
        ),
        VocabularyQuestion(
            id = 6,
            word = "Interpret",
            definition = "To explain or understand the meaning of something.",
            example = "The class learned how to interpret the information in the graph.",
            options = listOf(
                "To explain the meaning",
                "To copy exactly",
                "To hide information",
                "To make something shorter",
            ),
            correctAnswer = "To explain the meaning",
        ),
        VocabularyQuestion(
            id = 7,
            word = "Contrast",
            definition = "To compare things in order to show their differences.",
            example = "Contrast the two characters and explain how their choices differ.",
            options = listOf(
                "To show differences",
                "To prove two things are identical",
                "To list items randomly",
                "To repeat an answer",
            ),
            correctAnswer = "To show differences",
        ),
        VocabularyQuestion(
            id = 8,
            word = "Evaluate",
            definition = "To judge the quality, value, or effectiveness of something.",
            example = "We evaluate each source before using it in our research.",
            options = listOf(
                "To judge quality or value",
                "To translate word for word",
                "To avoid making a decision",
                "To organise alphabetically",
            ),
            correctAnswer = "To judge quality or value",
        ),
        VocabularyQuestion(
            id = 9,
            word = "Accurate",
            definition = "Correct and free from mistakes.",
            example = "Check that the information in your report is accurate.",
            options = listOf(
                "Correct and precise",
                "Creative but incorrect",
                "Long and complicated",
                "Easy to replace",
            ),
            correctAnswer = "Correct and precise",
        ),
        VocabularyQuestion(
            id = 10,
            word = "Consequence",
            definition = "A result or effect of an action or situation.",
            example = "One consequence of poor planning can be missed deadlines.",
            options = listOf(
                "A result of an action",
                "A plan made in advance",
                "A question with no answer",
                "A person who gives advice",
            ),
            correctAnswer = "A result of an action",
        ),
    )
}
