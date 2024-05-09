package com.hd.misaleawianegager.domain.local

import android.content.Context
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow

interface TextService {

    fun readTexts(context : Context) : Flow<Resources<String>>
    fun writeTexts(context: Context) : Boolean
    fun readRandomTexts(context: Context) :  Flow<Resources<String>>
    fun readSingleText(context: Context) : String
}