package com.example.devicepopulation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.devicepopulation.mockdata.DeviceModuleDataMock
import com.example.devicepopulation.ui.components.DeviceCardComponent
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import org.junit.Rule
import org.junit.Test

class DeviceDetailsScreenTest {

    private val mockDeviceModel = DeviceModuleDataMock().deviceModel1

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun basicUITest() {
        composeTestRule.setContent {
            DeviceAppTheme() {
                DeviceCardComponent(
                    device = mockDeviceModel,
                    onDeviceClick = {}
                )
            }
        }
        composeTestRule
            .onNodeWithContentDescription("Device Image")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Device Name")
            .assertIsDisplayed()
            .assertTextContains(mockDeviceModel.name)
        composeTestRule
            .onNodeWithContentDescription("Device Type")
            .assertIsDisplayed()
            .assertTextContains(mockDeviceModel.type)
        composeTestRule
            .onNodeWithContentDescription("Device Status")
            .assertIsDisplayed()
            .assertTextContains(mockDeviceModel.status)
    }
}
