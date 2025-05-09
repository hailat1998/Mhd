package com.hd.misaleawianegager.data.remote

import android.util.Log
import com.hd.misaleawianegager.BuildConfig
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProverbApiImpl @Inject constructor(private val client: HttpClient) : ProverbApi {

    companion object {
       private const val BASE_URL = BuildConfig.BASE_URL
    }

    override suspend fun meaning(proverb: String): ProverbResponse {
        return client.post("$BASE_URL/meaning") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("proverb" to proverb)) // or "proverb" depending on server
            expectSuccess = true
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("proverb" to proverb)}")
        }.body<ProverbResponse>().also {
            Log.i("API", "Received response: $it")
        }
    }

    override suspend fun la2am(latinAmharicText: String): String {

        return client.post("$BASE_URL/translate/la2am"){
            contentType(ContentType.Application.Json)
            setBody(mapOf("text" to latinAmharicText))
            expectSuccess = true
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("text" to latinAmharicText)}")
        }.body<String>().also {
            Log.i("API", "Received response: $it")
        }
    }

    override suspend fun en2am(englishText: String): String {

        return client.post("$BASE_URL/translate/en2am"){
            contentType(ContentType.Application.Json)
            setBody(mapOf("text" to englishText))
            expectSuccess = true
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("text" to englishText)}")
        }.body<String>().also {
            Log.i("API", "Received response: $it")
        }
    }

    override suspend fun enOrLa(laOren: String): String {
        Log.i("API", "Sending request to $BASE_URL")
        return client.post("$BASE_URL/translate/enOrLa2am"){
            contentType(ContentType.Application.Json)
            setBody(mapOf("laOren" to laOren))
            expectSuccess = true
        }.body<String>().also {
            Log.i("API", "Received response: $it")
        }
    }
}