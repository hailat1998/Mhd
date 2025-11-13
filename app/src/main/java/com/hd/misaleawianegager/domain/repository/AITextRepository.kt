package com.hd.misaleawianegager.domain.repository

import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow

interface AITextRepository {
    fun getFromNetwork(proverb: String): Flow<Resources<ProverbResponse>>

    fun laOren2am(latinAmharicText: String): Flow<Resources<String>>

    fun en2am(englishText: String): Flow<Resources<String>>

}