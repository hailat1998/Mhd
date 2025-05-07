package com.hd.misaleawianegager

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.presentation.component.selected.DetailEvent
import com.hd.misaleawianegager.presentation.component.selected.DetailUiState
import com.hd.misaleawianegager.presentation.component.selected.DetailViewModel
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.utils.Resources
import com.hd.misaleawianegager.utils.compose.favList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.time.Duration.Companion.seconds





@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockTextRepository: TextRepository

    @Mock
    private lateinit var mockContext: Context

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(mockTextRepository, testDispatcher, mockContext)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEvent LoadLetter updates detailStateFlow with data from textRepository`() = runTest(testDispatcher) {

        val query =  "ሀ"
        val assetFileName = DataProvider.letterMap[query]!!
        val expectedText = "Text from asset A"

        val flow = flow {
            emit(Resources.Success(expectedText))
        }

        `when`(mockTextRepository.readTextAsset(mockContext, assetFileName)).thenReturn(flow)

        viewModel.detailStateFlow.test {

            assertThat(awaitItem()).isEqualTo(emptyList<String>())

            viewModel.onEvent(DetailEvent.LoadLetter(query))

            testDispatcher.scheduler.advanceUntilIdle()

            val result = awaitItem()
            assertThat(result).isEqualTo(listOf(expectedText.trim()))

            cancelAndIgnoreRemainingEvents()
        }

        verify(mockTextRepository).readTextAsset(mockContext, assetFileName)
    }

    @Test
    fun `onEvent LoadFav updates detailStateFlow with favList`() = runTest(testDispatcher) {
        viewModel.detailStateFlow.test {
            assertThat(awaitItem()).isEqualTo(emptyList<String>())

            viewModel.onEvent(DetailEvent.LoadFav)

            val result = awaitItem()
            assertThat(result).isEqualTo(favList)
            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `onEvent LoadRecent updates detailStateFlow with reversed data from textRepository`() = runTest(testDispatcher) {
        val fileType = 1
        val text1 = "Recent Text 1"
        val text2 = "Recent Text 2"
        val flow = flowOf(Resources.Success(text1), Resources.Success(text2))

        `when`(mockTextRepository.readTextFile(mockContext, fileType)).thenReturn(flow)


        viewModel.detailStateFlow.test {
            assertThat(awaitItem()).isEqualTo(emptyList<String>()) // Initial state

            viewModel.onEvent(DetailEvent.LoadRecent)

            val result = awaitItem()
            assertThat(result).isEqualTo(listOf(text2, text1)) // reversed order

            cancelAndIgnoreRemainingEvents()
        }
        verify(mockTextRepository).readTextFile(mockContext, fileType)
    }


    @Test
    fun `onEvent LoadAIContent updates detailsAITextStateFlow with Loading then Success`() = runTest(testDispatcher) {
        val proverb = "Test Proverb"
        val proverbResponse = ProverbResponse("English Meaning", "Amharic Meaning")


        val loadingFlow = flow {
            emit(Resources.Loading(true))
            delay(2)
            emit(Resources.Success(proverbResponse))
        }

        `when`(mockTextRepository.getFromNetwork(proverb)).thenReturn(loadingFlow)

        viewModel.detailsAITextStateFlow.test(timeout = 5.seconds) {

            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(DetailUiState())


            viewModel.onEvent(DetailEvent.LoadAIContent(proverb))


            testDispatcher.scheduler.advanceUntilIdle()


            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.enMeaning).isNull()
            assertThat(loadingState.amMeaning).isNull()
            assertThat(loadingState.error).isNull()


            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.enMeaning).isEqualTo(proverbResponse.enMeaning)
            assertThat(successState.amMeaning).isEqualTo(proverbResponse.amMeaning)
            assertThat(successState.error).isNull()

            cancelAndIgnoreRemainingEvents()
        }
        verify(mockTextRepository).getFromNetwork(proverb)
    }

    @Test
    fun `onEvent LoadAIContent updates detailsAITextStateFlow with Error`() = runTest(testDispatcher) {
        val proverb = "Test Proverb Error"
        val errorMessage = "Network Error"

        val errorFlow = flow {
            emit(Resources.Loading<ProverbResponse>(true))
            delay(10)
            emit(Resources.Error<ProverbResponse>(errorMessage))
        }

        `when`(mockTextRepository.getFromNetwork(proverb)).thenReturn(errorFlow)

        viewModel.detailsAITextStateFlow.test {
            // Initial state
            assertThat(awaitItem()).isEqualTo(DetailUiState())

            // Trigger the event
            viewModel.onEvent(DetailEvent.LoadAIContent(proverb))

            // Process coroutines
            testDispatcher.scheduler.advanceUntilIdle()

            // Verify loading state
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.enMeaning).isNull()
            assertThat(loadingState.amMeaning).isNull()
            assertThat(loadingState.error).isNull()

            // Verify error state
            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.enMeaning).isNull()
            assertThat(errorState.amMeaning).isNull()
            assertThat(errorState.error).isEqualTo(errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
        verify(mockTextRepository).getFromNetwork(proverb)
    }

    @Test
    fun `detailFeedRecent handles empty data gracefully`() = runTest(testDispatcher) {
        val fileType = 1
        val flowWithEmptyData = flowOf(Resources.Success(""))

        `when`(mockTextRepository.readTextFile(mockContext, fileType)).thenReturn(flowWithEmptyData)

        viewModel.detailStateFlow.test {
            assertThat(awaitItem()).isEqualTo(emptyList<String>())
            viewModel.onEvent(DetailEvent.LoadRecent)
            val result = awaitItem()
            assertThat(result).isEqualTo(listOf(""))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `detailFeedRecent handles empty data from readTextFile gracefully`() = runTest(testDispatcher) {
        val fileType = 1
        val flowWithEmptyData = flowOf(Resources.Success(""))

        `when`(mockTextRepository.readTextFile(mockContext, fileType)).thenReturn(flowWithEmptyData)

        viewModel.detailStateFlow.test {
            assertThat(awaitItem()).isEqualTo(emptyList<String>())

            viewModel.onEvent(DetailEvent.LoadRecent)

            val result = awaitItem()

            assertThat(result).isEqualTo(listOf(""))

            cancelAndIgnoreRemainingEvents()
        }

        verify(mockTextRepository).readTextFile(mockContext, fileType)
    }
}