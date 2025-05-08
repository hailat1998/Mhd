package com.hd.misaleawianegager.network

import android.util.Log
import com.hd.misaleawianegager.data.remote.ProverbApiImpl
import com.hd.misaleawianegager.di.NetworkModule
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.network.ApiServiceTest.TestMockEngineConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class] // Replace your actual network module
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideTestMockEngineConfig(): TestMockEngineConfig {
        return TestMockEngineConfig()
    }

    @Provides
    @Singleton
    fun provideMockEngine(testMockEngineConfig: TestMockEngineConfig): HttpClientEngine {
        return MockEngine { request -> // 'this' here is MockRequestHandleScope
            Log.d("MockEngine", "Handling request: ${request.method.value} ${request.url.encodedPath}")
            // Correctly invoke the responseHandler with the MockRequestHandleScope
            testMockEngineConfig.responseHandler.invoke(this, request)
        }
    }

    // provideHttpClient, provideJson, provideProverbApi, provideCoroutineDispatcher remain the same
    @Provides
    @Singleton
    fun provideHttpClient(engine: HttpClientEngine, json: Json): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    classDiscriminator = "type"
                })
            }
            expectSuccess = true
        }

    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun provideProverbApi(httpClient: HttpClient): ProverbApi {
        return ProverbApiImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}