package com.hd.misaleawianegager.domain.local

import android.content.Context
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow

interface AssetsTextService {

    fun readTexts(context : Context , type : Int) : Flow<Resources<String?>>

    fun readRandomTexts(context: Context) : Flow<Resources<String>>

}