package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

const val RECENT  = "/local/recent.txt"
const val FAV = "local/fav.txt"
class FileServiceImp : FileService {
    override fun readTexts(context: Context, type: Int): Flow<Resources<String>> {

        val readType = if (type == 1) RECENT else FAV

        return flow {
            context.openFileInput(readType).bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    emit(Resources.Success(line))
                }
            }
        }
    }

    override fun writeTexts(context: Context , type: Int  , text : String): Boolean {
       val writeType = if(type == 1) RECENT else FAV
        try {
            context.openFileOutput(writeType, Context.MODE_PRIVATE).use {
                it.write(text.toByteArray())
            }
        }catch(e : IOException){
            println(e)
            return false
        }
        return true
    }
}