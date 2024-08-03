package com.hd.misaleawianegager.data.local

import android.content.Context
import android.util.Log
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val RECENT  = "recent.txt"
const val FAV = "fav.txt"

@Singleton
class FileServiceImp @Inject constructor(@ApplicationContext context: Context) : FileService {

    init{
        val file1 = File(context.filesDir, FAV)
        val file2 = File(context.filesDir, RECENT)
        if(file1.createNewFile()){
            Log.i("FILESERVICE" , "file created: Fav")
        }

        if(file2.createNewFile()){
            Log.i("FILESERVICE" , "file created: Recent")
        }
    }
    override fun readTexts(context: Context, type: Int): Flow<String>{

            val readType = if (type == 1) RECENT else FAV

            Log.i("FILESERVICE" , "reading text")
            println(type)

        return flow {
            context.openFileInput(readType).bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    println(line)
                    emit(line)
                }
            }
        }.distinctUntilChanged()
    }

    override fun writeTexts(context: Context , type: Int  , text : String): Boolean {
       val writeType = if(type == 1) RECENT else FAV
        Log.i("FILESERVICE" , "writing text")

      val list  = mutableListOf<String>()
        try {
            context.openFileOutput(writeType,  if(type == 1 )Context.MODE_APPEND else Context.MODE_PRIVATE).use {
                it.write(text.plus("\n").toByteArray())
            }
        }catch(e : IOException){
            println(e)
            return false
        }
        return true
    }
}