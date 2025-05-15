package com.hd.misaleawianegager.network

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hd.misaleawianegager.data.remote.ProverbApiImpl
import com.hd.misaleawianegager.di.NetworkModule
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.request.HttpResponseData
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.readRemaining

//Before Running this tests make sure you commented the the custom WorkManager initializer in Manifest.


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ApiServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ProverbApi

    @Inject
    lateinit var mockEngineConfig: TestMockEngineConfig

    private val testJson = Json { ignoreUnknownKeys = true; prettyPrint = true; isLenient = true }

    @Singleton
    class TestMockEngineConfig {
        // MODIFIED SIGNATURE: Now takes MockRequestHandleScope as receiver
        var responseHandler: suspend MockRequestHandleScope.(request: HttpRequestData) -> HttpResponseData =
            { request -> // 'this' is MockRequestHandleScope
                Log.e("MockEngineConfig", "Unhandled request: ${request.url.encodedPath}")
                // Now 'respond' can be called directly as it's within the scope
                respond(
                    content = ByteReadChannel("""{"error":"Unhandled request by mock: ${request.url.encodedPath}"}"""),
                    status = HttpStatusCode.NotImplemented,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
    }

    @Before
    fun setup() {
        hiltRule.inject()
        Log.d(
            "ApiServiceTest",
            "Setup complete. ApiService injected: ${apiService != null}, MockEngineConfig injected: ${mockEngineConfig != null}"
        )
    }

    private suspend fun HttpRequestData.bodyToString(): String {
        val bodyBytes = this.body.toByteArray()
        return bodyBytes.decodeToString()
    }

    @Test
    fun testGetMeaning_success() {
        runBlocking {
            val proverbToTest = "A stitch in time saves nine"
            val expectedResponse = ProverbResponse(
                enMeaning = "Fixing a small problem early can prevent a larger problem later.",
                amMeaning = "You should fix that leaky faucet now; a stitch in time saves nine."
            )
            val responseJson =
                testJson.encodeToString(ProverbResponse.serializer(), expectedResponse)

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                // Instead of checking the exact string format, parse the JSON and check the content
                val requestBody = request.body.toByteArray().decodeToString()
                val jsonObject = org.json.JSONObject(requestBody)

                assertThat(request.url.encodedPath).isEqualTo("/meaning")
                assertThat(request.method).isEqualTo(HttpMethod.Post)
                assertThat(jsonObject.getString("proverb")).isEqualTo(proverbToTest)

                Log.d("MockEngine", "/meaning responding with OK")

                respond(
                    content = ByteReadChannel(responseJson),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            val actualResponse = apiService.meaning(proverbToTest)
            assertThat(actualResponse).isEqualTo(expectedResponse)
            Log.i("TestResult", "testGetMeaning_success: $actualResponse")
        }
    }

    @Test
    fun testGetMeaning_error() {
        runBlocking {
            val proverbToTest = "Error case"

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                assertThat(request.url.encodedPath).isEqualTo("/meaning")
                Log.d("MockEngine", "/meaning responding with InternalServerError")
                respond(
                    content = ByteReadChannel("""{"error":"Server failed"}"""),
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            var exception: Exception? = null
            try {
                apiService.meaning(proverbToTest)
            } catch (e: Exception) {
                exception = e
            }

            assertThat(exception).isNotNull()
            assertThat(exception).isInstanceOf(io.ktor.client.plugins.ServerResponseException::class.java)
            Log.i("TestResult", "testGetMeaning_error: $exception")
        }
    }

    @Test
    fun testLa2Am_success() {
        runBlocking {
            val textToTranslate = "selam"
            val expectedTranslation = "ሰላም"
            val responseJsonString = testJson.encodeToString(expectedTranslation)

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                val requestBody = request.body.toByteArray().decodeToString()
                val jsonObject = org.json.JSONObject(requestBody)

                assertThat(request.url.encodedPath).isEqualTo("/translate/la2am")
                assertThat(request.method).isEqualTo(HttpMethod.Post)
                assertThat(jsonObject.getString("text")).isEqualTo(textToTranslate)

                Log.d("MockEngine", "/translate/la2am responding with OK")

                respond(
                    content = ByteReadChannel(responseJsonString),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            val actualTranslation = apiService.la2am(textToTranslate)
            assert(actualTranslation.contains(expectedTranslation))
            Log.i("TestResult", "testLa2Am_success: $actualTranslation")
        }
    }
    @Test
    fun testEn2Am_success() {

        runBlocking {
            val textToTranslate = "hello"
            val expectedTranslation = "ሰላም"
            val responseJsonString = testJson.encodeToString(expectedTranslation)

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                assertThat(request.url.encodedPath).isEqualTo("/translate/en2am")
                assertThat(request.method).isEqualTo(HttpMethod.Post)
                assertThat(request.bodyToString()).contains("\"text\": \"$textToTranslate\"")
                Log.d("MockEngine", "/translate/en2am responding with OK")
                respond(
                    content = ByteReadChannel(responseJsonString),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            val actualTranslation = apiService.en2am(textToTranslate)
            assert(actualTranslation.contains(expectedTranslation))
            Log.i("TestResult", "testEn2Am_success: $actualTranslation")
        }
    }

    @Test
    fun testEnOrLa_success() {
        runBlocking {
            val textToTranslate = "selam"
            val expectedTranslation = "ሰላም"
            val responseJsonString = testJson.encodeToString(expectedTranslation)

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                assertThat(request.url.encodedPath).isEqualTo("/translate/enOrLa2am")
                assertThat(request.method).isEqualTo(HttpMethod.Post)
                assertThat(request.bodyToString()).contains("\"laOren\": \"$textToTranslate\"")
                Log.d("MockEngine", "/translate/enOrLa2am responding with OK")
                respond(
                    content = ByteReadChannel(responseJsonString),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            val actualTranslation = apiService.enOrLa(textToTranslate)
            assert(actualTranslation.contains(expectedTranslation))
            Log.i("TestResult", "testEnOrLa_success: $actualTranslation")
        }
    }

    @Test
    fun testLa2Am_error() {
        runBlocking {
            val textToTranslate = "error case"

            mockEngineConfig.responseHandler = { request -> // 'this' is MockRequestHandleScope
                assertThat(request.url.encodedPath).isEqualTo("/translate/la2am")
                respond(
                    content = ByteReadChannel("""{"error":"Translation failed"}"""),
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            var exception: Exception? = null
            try {
                apiService.la2am(textToTranslate)
            } catch (e: Exception) {
                exception = e
            }
            assertThat(exception).isNotNull()
            assertThat(exception).isInstanceOf(io.ktor.client.plugins.ClientRequestException::class.java)
            Log.i("TestResult", "testLa2Am_error: $exception")
        }
    }
}


//internal fun io.ktor.http.content.OutgoingContent.toByteArray(): ByteArray = when (this) {
//    is io.ktor.http.content.OutgoingContent.ByteArrayContent -> this.bytes()
//    is io.ktor.http.content.OutgoingContent.ReadChannelContent -> runBlocking { readFrom().toByteArray() }
//    is io.ktor.http.content.OutgoingContent.WriteChannelContent -> runBlocking {
//        ByteReadChannel("").apply { writeTo(this@toByteArray) }.toByteArray()
//    }
//    else -> throw UnsupportedOperationException("Unsupported OutgoingContent type to convert to ByteArray: ${this::class}")
//}
//internal suspend fun io.ktor.utils.io.ByteReadChannel.toByteArray() = readRemaining().readBytes()
