package com.vince.vocabquest.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.vince.vocabquest.ui.theme.VocabQuestTheme
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun landingScreen_showsTitleAndStartAction() {
        composeTestRule.setContent {
            VocabQuestTheme {
                LandingScreen(onStartLearning = {})
            }
        }

        composeTestRule.onNodeWithText("VocabQuest").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start learning").assertIsDisplayed()
    }
}
