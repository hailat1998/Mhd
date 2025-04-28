package com.hd.misaleawianegager.data.remote

import android.util.Log
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProverbApiImpl @Inject constructor(private val client: HttpClient) : ProverbApi {

    companion object {
        private const val BASE_URL = "https://misale-latest.onrender.com"
    }

    override suspend fun meaning(proverb: String): ProverbResponse {

        return client.post("$BASE_URL/meaning") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("text" to proverb)) // or "proverb" depending on server
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("text" to proverb)}")
        }.body<ProverbResponse>().also {
            Log.i("API", "Received response: $it")
        }
    }

    override suspend fun la2am(latinAmharicText: String): String {

        return client.post("$BASE_URL/translate/la2am"){
            setBody(mapOf("text" to latinAmharicText))
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("text" to latinAmharicText)}")
        }.body<String>().also {
            Log.i("API", "Received response: $it")
        }
    }

    override suspend fun en2am(englishText: String): String {

        return client.post("$BASE_URL/translate/en2am"){
            setBody(mapOf("text" to englishText))
            Log.i("API", "Sending request to: $BASE_URL/meaning with body: ${mapOf("text" to englishText)}")
        }.body<String>().also {
            Log.i("API", "Received response: $it")
        }
    }
}