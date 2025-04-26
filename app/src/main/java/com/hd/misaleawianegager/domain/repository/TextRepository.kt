package com.hd.misaleawianegager.domain.repository

import android.content.Context
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface TextRepository {
    fun readTextAsset(context: Context, type: String) : Flow<Resources<String>>

    fun readTextFile(context: Context,type: Int) : Flow<Resources<String>>

    fun writeTextFile(context: Context, type: Int, text: String) : Boolean

    fun search(context: Context, query: String): Flow<Resources<String>>

    fun enqueueWork(): Flow<String>

    fun getFromNetWork(proverb: String): Flow<Resources<ProverbResponse>>

    fun la2am(latinAmharicText: String): Flow<Resources<String>>

    fun en2am(englishText: String): Flow<Resources<String>>

    fun readSingle(): Flow<Resources<String>>
}