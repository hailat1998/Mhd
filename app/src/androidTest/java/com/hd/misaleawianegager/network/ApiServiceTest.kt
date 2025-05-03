package com.hd.misaleawianegager.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hd.misaleawianegager.domain.remote.ProverbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

//@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
//class ApiServiceTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    private lateinit var mockEngineConfig: MockEngineConfigLocal
//
//    @Inject
//    lateinit var apiService: ProverbApi
//
//    @Module
//    @InstallIn(SingletonComponent::class)
//    object TestModule {
//
//        @Provides
//        @Singleton
//        fun provideMockEngine(mockEngineConfig: MockEngineConfig): MockEngine {
//            return MockEngine { request ->
//                mockEngineConfig.responseHandler(request)
//            }
//        }
//
//        @Provides
//        @Singleton
//        fun provideMockEngineConfig(): MockEngineConfigLocal {
//            return MockEngineConfigLocal()
//        }
//
//        @Provides
//        @Singleton
//        fun provideCoroutineDispatcher(): CoroutineDispatcher =
//            kotlinx.coroutines.Dispatchers.IO
//    }
//
//    // Create a custom class to handle mock responses
//    class MockEngineConfigLocal {
//        var responseHandler: MockEngine.Config.() -> HttpResponseData = {
//            // Default response
//            respond(
//                content = ByteReadChannel("{}"),
//                status = HttpStatusCode.OK,
//                headers = headersOf(HttpHeaders.ContentType, "application/json")
//            )
//        }
//    }
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//        // Get the injected mockEngineConfig
//        mockEngineConfig = TestModule.provideMockEngineConfig()
//    }
//
//    @Test
//    fun testGetUsersReturnsSuccessfulResponse() = runBlocking {
//        // Prepare test response
//        val responseJson = """
//            {
//                "users": [
//                    {"id": 1, "name": "John Doe"},
//                    {"id": 2, "name": "Jane Smith"}
//                ]
//            }
//        """.trimIndent()
//
//        // Configure mock response
//        mockEngineConfig.responseHandler = { request ->
//            if (request.url.encodedPath == "/users" && request.method == HttpMethod.Get) {
//                respond(
//                    content = ByteReadChannel(responseJson),
//                    status = HttpStatusCode.OK,
//                    headers = headersOf(HttpHeaders.ContentType, "application/json")
//                )
//            } else {
//                respond(
//                    content = ByteReadChannel("Not found"),
//                    status = HttpStatusCode.NotFound
//                )
//            }
//        }
//
//        // Execute the API call
//        val result = apiService.getUsers()
//
//        // Verify the result
//        assertThat(result.isSuccess, equalTo(true))
//        val users = result.getOrNull()
//        assertThat(users, notNullValue())
//        assertThat(users, hasSize(2))
//        assertThat(users!![0].name, equalTo("John Doe"))
//        assertThat(users[1].name, equalTo("Jane Smith"))
//    }
//
//    @Test
//    fun testGetUsersHandlesErrorResponse() = runBlocking {
//        // Configure mock to return an error
//        mockEngineConfig.responseHandler = { _ ->
//            MockEngine.respond(
//                content = ByteReadChannel("Internal server error"),
//                status = HttpStatusCode.InternalServerError
//            )
//        }
//
//        // Execute the API call
//        val result = apiService.getUsers()
//
//        // Verify the result
//        assertThat(result.isFailure, equalTo(true))
//        val exception = result.exceptionOrNull()
//        assertThat(exception, notNullValue())
//    }
//
//    // Helper class to manage mock responses
//    class MockEngineConfig {
//        var responseHandler: (HttpRequestData) -> HttpResponseData = { request ->
//            throw IllegalStateException("Response handler not configured for request: ${request.url}")
//        }
//    }
//}