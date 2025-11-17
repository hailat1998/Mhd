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
       file1.createNewFile()

       file2.createNewFile()
    }
    override fun readTexts(context: Context, type: Int): Flow<String>{

        val readType = if (type == 1) RECENT else FAV

        return flow {
            context.openFileInput(readType).bufferedReader().useLines { lines ->
                lines.forEach { line ->
              //      println(line)
                    emit(line)
                }
            }
        }.distinctUntilChanged()
    }

    override fun writeTexts(context: Context, type: Int, text: String): Boolean {
        val writeType = if (type == 1) RECENT else FAV

        try {

            val existingContent = readExistingContent(context, writeType)

            if (!existingContent.contains(text)) {

                val updatedContent = if (existingContent.isEmpty()) {
                    text
                } else {
                    "$existingContent\n$text"
                }

                context.openFileOutput(writeType, Context.MODE_PRIVATE).use { outputStream ->
                    outputStream.write(updatedContent.toByteArray())
                }
            } else {

                val list = existingContent.split("\n").toMutableList()
                list.remove(text)
                list.add(text)

                val updatedContent = list.joinToString("\n")

                context.openFileOutput(writeType, Context.MODE_PRIVATE).use { outputStream ->
                    outputStream.write(updatedContent.toByteArray())
                }
            }
        } catch (e: IOException) {
            println(e)
            return false
        }

        return true
    }

    // Helper function to read existing content
    private fun readExistingContent(context: Context, fileName: String): String {
        return try {
            context.openFileInput(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            // Return empty string if file doesn't exist or can't be read
            ""
        }
    }
}