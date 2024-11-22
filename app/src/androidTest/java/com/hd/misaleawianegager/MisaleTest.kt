package com.hd.misaleawianegager

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class MisaleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun misaleTest() {
        composeTestRule.setContent {
            MisaleawiAnegagerTheme(
                theme = "light",
                selectedFont = mutableStateOf("desta_gentium"),
                fontSize = mutableStateOf(23) ,
                letterSpace = mutableStateOf(1.0),
                letterHeight = mutableStateOf(34)
            ) {
               HomeContent(homeData = mutableStateOf(emptyList()), onHomeEvent = {}, onSettingEvent = {} , scrollIndex = mutableStateOf(0), toDetail = {_,_,_ -> return@HomeContent })
            }
        }

       composeTestRule.onNodeWithText("Home").assertIsDisplayed()
       composeTestRule.onNodeWithContentDescription("INFO").assertHasClickAction()
       composeTestRule.onNodeWithTag("FLOAT").assertHasClickAction()
    }

    fun recentTest(){

    }

}