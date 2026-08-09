package com.vince.vocabquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vince.vocabquest.ui.VocabQuestApp
import com.vince.vocabquest.ui.theme.VocabQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as VocabQuestApplication).container

        setContent {
            VocabQuestTheme {
                VocabQuestApp(
                    vocabularyRepository = container.vocabularyRepository,
                    progressRepository = container.progressRepository,
                    dictionaryRepository = container.dictionaryRepository,
                )
            }
        }
    }
}
