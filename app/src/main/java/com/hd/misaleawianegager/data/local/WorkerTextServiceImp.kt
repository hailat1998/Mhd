package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.domain.local.WorkerTextService
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
class WorkerTextServiceImp @Inject constructor() : WorkerTextService {

    override fun readSingleText(context: Context): String {
        val assets = context.assets
        val listAssets = assets.list("text/") ?: return ""
        if (listAssets.isEmpty()) return ""

        val randomIndex = Random.nextInt(listAssets.size)
        val randomFile = listAssets[randomIndex]


        val inputStream = assets.open("text/$randomFile")
        val reader = BufferedReader(InputStreamReader(inputStream))


        val lines = reader.use { it.readLines() }


        if (lines.isEmpty()) return ""


        val randomLineIndex = Random.nextInt(lines.size)
        val randomLine = lines[randomLineIndex]

        return randomLine
    }
}