package com.hd.misaleawianegager.utils

import com.hd.misaleawianegager.domain.remote.ProverbResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CacheManagerImp @Inject constructor(private val hashMap: HashMap<String, ProverbResponse> ): CacheManager {
    override fun set(proverbResponse: ProverbResponse, proverb: String) {
        hashMap[proverb] = proverbResponse
    }

    override fun get(proverb: String) : ProverbResponse? {
        return hashMap[proverb]
    }

    override fun contains(proverb: String): Boolean {
       return hashMap.contains(proverb)
    }
}