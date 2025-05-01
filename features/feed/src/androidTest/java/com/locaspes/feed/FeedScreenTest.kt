package com.locaspes.feed

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.google.firebase.Timestamp
import com.locaspes.data.model.ProjectCard
import com.locaspes.ui.FeedScreen
import com.locaspes.ui.FeedUiState
import com.locaspes.ui.FeedViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test


class FeedScreenTest {
    @get: Rule
    val composeRule = createComposeRule()

    private val mockViewModel = mockk<FeedViewModel>()

    @Test
    fun test(){
        composeRule.setContent {
            FeedScreen(viewModel = mockViewModel)
        }
        composeRule.onNodeWithText("D").assertExists()
    }

}