package com.example.realestatemanagersamuelrogeron

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realestatemanagersamuelrogeron.ui.add_screen.AddEstatePhoneScreen
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddEstatePhoneScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddEstatePhoneScreenUI() {
        composeTestRule.setContent {
            AppTheme {
                AddEstatePhoneScreen(
                    onBackPress = {},
                    onSavePress = {},
                    uiState = AddEstateState(),
                    onFieldChange = { _, _ -> },
                    onMediaSelected = {},
                    onImageCaptured = {},
                    onMediaRemoved = {},
                    onInterestPointsSelected = {},
                    onInterestPointCreated = { _, _ -> },
                    onInterestPointItemRemove = {}
                )
            }
        }

        // Verify that the main components are present
        composeTestRule.onNodeWithText("Add New Estate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Select Pictures").assertIsDisplayed()
        composeTestRule.onNodeWithText("Type").assertIsDisplayed()
        composeTestRule.onNodeWithText("Etage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rooms").assertIsDisplayed()
        composeTestRule.onNodeWithText("Surface").assertIsDisplayed()
        composeTestRule.onNodeWithText("Offer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Price").assertIsDisplayed()
        composeTestRule.onNodeWithText("Address").assertIsDisplayed()
        composeTestRule.onNodeWithText("City").assertIsDisplayed()
        composeTestRule.onNodeWithText("Region").assertIsDisplayed()
        composeTestRule.onNodeWithText("Country").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Interest Points").assertIsDisplayed()
    }

    @Test
    fun testFieldInteractions() {
        composeTestRule.setContent {
            AppTheme {
                AddEstatePhoneScreen(
                    onBackPress = {},
                    onSavePress = {},
                    uiState = AddEstateState(),
                    onFieldChange = { _, _ -> },
                    onMediaSelected = {},
                    onImageCaptured = {},
                    onMediaRemoved = {},
                    onInterestPointsSelected = {},
                    onInterestPointCreated = { _, _ -> },
                    onInterestPointItemRemove = {}
                )
            }
        }

        // Test interaction with the Title field
        composeTestRule.onNodeWithText("Title").performTextInput("Test Estate")
        composeTestRule.onNodeWithText("Test Estate").assertIsDisplayed()

        // Test interaction with the Price field
        composeTestRule.onNodeWithText("Price").performTextInput("250000")
        composeTestRule.onNodeWithText("250000").assertIsDisplayed()

        // Test interaction with the Surface field
        composeTestRule.onNodeWithText("Surface").performTextInput("100")
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
    }

    @Test
    fun testErrorStates() {
        composeTestRule.setContent {
            AppTheme {
                AddEstatePhoneScreen(
                    onBackPress = {},
                    onSavePress = {},
                    uiState = AddEstateState(
                        titleError = true,
                        priceError = true,
                        surfaceError = true
                    ),
                    onFieldChange = { _, _ -> },
                    onMediaSelected = {},
                    onImageCaptured = {},
                    onMediaRemoved = {},
                    onInterestPointsSelected = {},
                    onInterestPointCreated = { _, _ -> },
                    onInterestPointItemRemove = {}
                )
            }
        }

        // Verify that error states are displayed
        composeTestRule.onNodeWithTag("TitleError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PriceError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SurfaceError").assertIsDisplayed()
    }
}


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EstateAdditionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addEstateAndVerifyInList() {
        // Start on EstateListScreen
        composeTestRule.onNodeWithTag("estate_list").assertIsDisplayed()

        // Navigate to AddEstateScreen
        composeTestRule.onNodeWithContentDescription("Add Estate").performClick()

        // Fill in estate details
        composeTestRule.onNodeWithTag("title_input").performTextInput("Test Estate")
        composeTestRule.onNodeWithTag("price_input").performTextInput("250000")
        composeTestRule.onNodeWithTag("surface_input").performTextInput("100")
        composeTestRule.onNodeWithTag("rooms_input").performTextInput("3")
        composeTestRule.onNodeWithTag("address_input").performTextInput("123 Test St")
        composeTestRule.onNodeWithTag("city_input").performTextInput("Test City")

        // Save the new estate
        composeTestRule.onNodeWithText("Save").performClick()

        // Wait for navigation back to EstateListScreen
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("estate_list_item").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify that the new estate appears in the list
        composeTestRule.onNodeWithText("Test Estate").assertIsDisplayed()
        composeTestRule.onNodeWithText("$250,000").assertIsDisplayed()
        composeTestRule.onNodeWithText("100 m²").assertIsDisplayed()
    }
}