package com.example.devicepopulation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.text.input.TextFieldValue
import com.example.devicepopulation.ui.components.SearchBar
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import org.junit.Rule
import org.junit.Test

class TopSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun inputSearchNotFocused() {
        composeTestRule.setContent {
            DeviceAppTheme() {
                SearchBar(
                    query = TextFieldValue(text = ""),
                    onQueryChange = {},
                    searchFocused = false,
                    onSearchFocusChange = {},
                    onClearQuery = { /*TODO*/ },
                    searching = false
                )
            }
        }
        composeTestRule
            .onNodeWithContentDescription("Input Search")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("hint perform search")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertDoesNotExist()
    }

    @Test
    fun inputSearchWithText() {
        composeTestRule.setContent {
            DeviceAppTheme() {
                SearchBar(
                    query = TextFieldValue(text = "Test"),
                    onQueryChange = {},
                    searchFocused = false,
                    onSearchFocusChange = {},
                    onClearQuery = { /*TODO*/ },
                    searching = false
                )
            }
        }
        composeTestRule
            .onNodeWithContentDescription("hint perform search")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithContentDescription("Input Search")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
    }
}
