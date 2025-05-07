package com.hd.misaleawianegager.domain.remote

import java.io.IOException

interface ProverbApi {

    @Throws(IOException::class)
    suspend fun meaning(proverb: String): ProverbResponse
    suspend fun la2am(latinAmharicText: String): String
    suspend fun en2am(englishText: String): String

    @Throws(IOException::class)
    suspend fun enOrLa(laOren: String): String

}