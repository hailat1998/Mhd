package com.hd.misaleawianegager.domain.local

import android.content.Context
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface AssetsTextService {

    fun readTexts(context : Context , type : String) : Flow<String?>

    fun readRandomTexts(context: Context) : Flow<String>

    fun search(context: Context, query: String): Flow<String>

}