package com.hd.misaleawianegager.utils

import com.hd.misaleawianegager.domain.remote.ProverbResponse

interface CacheManager {
    fun set(proverbResponse: ProverbResponse, proverb: String)
    fun get(proverb: String): ProverbResponse?
    fun contains(proverb: String): Boolean
}