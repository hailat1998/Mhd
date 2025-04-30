package com.hd.misaleawianegager.domain.remote

interface ProverbApi {
    suspend fun meaning(proverb: String): ProverbResponse
    suspend fun la2am(latinAmharicText: String): String
    suspend fun en2am(englishText: String): String
    suspend fun enOrLa(laOren: String): String
}