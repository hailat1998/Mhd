package com.hd.misaleawianegager.data.remote

import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject


class ProverbApiImpl @Inject constructor(private val client: HttpClient) : ProverbApi {

    companion object {
        private const val BASE_URL = "https://api.example.com"
    }

    override suspend fun meaning(proverb: String): ProverbResponse {
       return client.post("$BASE_URL/meaning"){
           setBody(proverb)
       }.body<ProverbResponse>()
    }

    override suspend fun la2am(latinAmharicText: String): String {
        return client.post("$BASE_URL/la2am"){
            setBody(latinAmharicText)
        }.body()
    }

    override suspend fun en2am(englishText: String): String {
        return client.post("$BASE_URL/en2am"){
            setBody(englishText)
        }.body()
    }

}