package com.hd.misaleawianegager

import android.content.Context
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.repository.TextRepositoryImpl
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.utils.CacheManager
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TextRepositoryImplTest {

    @Mock
    private lateinit var mockAssetsTextService: AssetsTextService

    @Mock
    private lateinit var mockFileService: FileService

    @Mock
    private lateinit var mockWorkManager: WorkManager

    @Mock
    private lateinit var mockApi: ProverbApi

    @Mock
    private lateinit var mockCacheManager: CacheManager

    @Mock
    private lateinit var mockWorkerTextService: WorkerTextService

    @Mock
    private lateinit var mockContext: Context // ApplicationContext

    private lateinit var textRepository: TextRepositoryImpl




    @Before
    fun setUp() {
        // If enMap were static/companion and needed setup:
        // TextRepositoryImpl.enMap = mapOf("hello" to "ሰላም") // Example
        textRepository = TextRepositoryImpl(
            assetsTextService = mockAssetsTextService,
            fileService = mockFileService,
            workManager = mockWorkManager,
            api = mockApi,
            cacheManager = mockCacheManager,
            workerTextService = mockWorkerTextService,
            context = mockContext
        )
    }

    @Test
    fun `readTextAsset emits Success when data is available`() = runTest {
        val fakeType = "greetings"
        val fakeData = "Hello, World!"
        `when`(mockAssetsTextService.readTexts(mockContext, fakeType)).thenReturn(flowOf(fakeData))

        val result = textRepository.readTextAsset(mockContext, fakeType).toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resources.Success)
        assertEquals(fakeData, (result[0] as Resources.Success).data)
        verify(mockAssetsTextService).readTexts(mockContext, fakeType)
    }

    @Test
    fun `readTextAsset emits Loading when data is null initially`() = runTest {

        val fakeType = "greetings"
        `when`(mockAssetsTextService.readTexts(mockContext, fakeType)).thenReturn(flowOf(null))

        val result = textRepository.readTextAsset(mockContext, fakeType).toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resources.Loading)
        assertTrue((result[0] as Resources.Loading).isLoading)
        verify(mockAssetsTextService).readTexts(mockContext, fakeType)

    }

    @Test
    fun `search emits Success with result`() = runTest {

        val query = "test query"
        val searchResult = "Found item"
        `when`(mockAssetsTextService.search(mockContext, query)).thenReturn(flowOf(searchResult))

        val result = textRepository.search(mockContext, query).first()

        assertTrue(result is Resources.Success)
        assertEquals(searchResult, result.data)
        verify(mockAssetsTextService).search(mockContext, query)

    }

    @Test
    fun `readTextFile emits Success with result`() = runTest {

        val type = 1
        val fileContent = "Content from file"
        `when`(mockFileService.readTexts(mockContext, type)).thenReturn(flowOf(fileContent))

        val result = textRepository.readTextFile(mockContext, type).first()

        assertTrue(result is Resources.Success)
        assertEquals(fileContent, result.data)
        verify(mockFileService).readTexts(mockContext, type)

    }

    @Test
    fun `writeTextFile returns result from fileService`() {
        val type = 1
        val text = "Text to write"
        `when`(mockFileService.writeTexts(mockContext, type, text)).thenReturn(true)

        val result = textRepository.writeTextFile(mockContext, type, text)

        assertTrue(result)
        verify(mockFileService).writeTexts(mockContext, type, text)
    }

    @Test
    fun `getFromNetwork emits Success from cache if available`() = runTest {

        val proverb = "testProverb"
        val cachedResponse = ProverbResponse("id1", "Cached meaning")
        `when`(mockCacheManager.contains(proverb)).thenReturn(true)
        `when`(mockCacheManager.get(proverb)).thenReturn(cachedResponse)

        val results = textRepository.getFromNetwork(proverb).toList()

        // Expected emissions: Loading(true), Success(cachedResponse), Loading(false)
        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Success)
        assertEquals(cachedResponse, (results[1] as Resources.Success).data)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)

        verify(mockCacheManager).contains(proverb)
        verify(mockCacheManager).get(proverb)

    }

    @Test
    fun `getFromNetwork fetches from API and caches on cache miss`() = runTest {

        val proverb = "testProverb"
        val apiResponse = ProverbResponse("id2", "API meaning")
        `when`(mockCacheManager.contains(proverb)).thenReturn(false)
        `when`(mockApi.meaning(proverb)).thenReturn(apiResponse)

        val results = textRepository.getFromNetwork(proverb).toList()

        // Expected emissions: Loading(true), Success(apiResponse), Loading(false)
        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Success)
        assertEquals(apiResponse, (results[1] as Resources.Success).data)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)

        verify(mockCacheManager).contains(proverb)
        verify(mockApi).meaning(proverb)
        verify(mockCacheManager).set(apiResponse, proverb)
    }

    @Test
    fun `getFromNetwork emits Error on API IOException`() = runTest {

        val proverb = "testProverb"
        val errorMessage = "Network failed"
        `when`(mockCacheManager.contains(proverb)).thenReturn(false)
        `when`(mockApi.meaning(proverb)).thenThrow(IOException(errorMessage))

        val results = textRepository.getFromNetwork(proverb).toList()

        // Expected emissions: Loading(true), Error, Loading(false)
        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Error)
        assertEquals("Network error: $errorMessage", (results[1] as Resources.Error).message)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)

        verify(mockCacheManager).contains(proverb)
        verify(mockApi).meaning(proverb)
    }

    @Test
    fun `getFromNetwork emits Error on general API Exception`() = runTest {

        val proverb = "testProverb"
        val errorMessage = "Some API error"
        `when`(mockCacheManager.contains(proverb)).thenReturn(false)
        `when`(mockApi.meaning(proverb)).thenThrow(RuntimeException(errorMessage))

        val results = textRepository.getFromNetwork(proverb).toList()

        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Error)
        assertEquals("Error fetching proverb meaning: $errorMessage", (results[1] as Resources.Error).message)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)
    }


    @Test
    fun `laOren2am emits Success on API success`() = runTest {
        val latinText = "selam"
        val amharicText = "ሰላም"
        `when`(mockApi.enOrLa(latinText)).thenReturn(amharicText)

        val results = textRepository.laOren2am(latinText).toList()

        // Expected: Loading(true), Success, Loading(false)
        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Success)
        assertEquals(amharicText, (results[1] as Resources.Success).data)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)

        verify(mockApi).enOrLa(latinText)
    }

    @Test
    fun `laOren2am emits Error on API IOException`() = runTest {
        val latinText = "selam"
        val errorMessage = "Network error during laOren2am"
        `when`(mockApi.enOrLa(latinText)).thenThrow(IOException(errorMessage))

        val results = textRepository.laOren2am(latinText).toList()

        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Error)
        assertEquals("Network error: $errorMessage", (results[1] as Resources.Error).message)
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)

    }


    @Test
    fun `en2am emits Error for unknown English text (NullPointerException)`() = runTest {
        val unknownEnglishText = "unknown_word_for_enMap"
        // This will cause enMap[englishText]!! to throw NullPointerException in the SUT

        val results = textRepository.en2am(unknownEnglishText).toList()

        assertEquals(3, results.size)
        assertTrue(results[0] is Resources.Loading && (results[0] as Resources.Loading).isLoading)
        assertTrue(results[1] is Resources.Error)
        // The exact message might depend on Kotlin version or how NPE is wrapped
        assertTrue((results[1] as Resources.Error).message!!.startsWith("Error fetching proverb meaning:"))
        assertTrue(results[2] is Resources.Loading && !(results[2] as Resources.Loading).isLoading)
    }

    @Test
    fun `readSingle emits Success with data from workerTextService`() = runTest {
        val singleText = "Single text from worker"
        `when`(mockWorkerTextService.readSingleText(mockContext)).thenReturn(singleText)

        val result = textRepository.readSingle().first()

        assertTrue(result is Resources.Success)
        assertEquals(singleText, result.data)
        verify(mockWorkerTextService).readSingleText(mockContext)
    }
}
