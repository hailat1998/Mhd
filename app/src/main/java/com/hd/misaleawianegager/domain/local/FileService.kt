package com.hd.misaleawianegager.domain.local

import android.content.Context
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow

interface FileService {

    fun readTexts(context : Context, type : Int) : Flow<Resources<String>>
    fun writeTexts(context: Context, type : Int , text : String) : Boolean

}