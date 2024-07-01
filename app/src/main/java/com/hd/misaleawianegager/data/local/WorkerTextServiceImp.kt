package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.utils.Resources
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader
import kotlin.random.Random

class WorkerTextServiceImp : WorkerTextService {

    override fun readSingleText(context: Context): String {
        val assets = context.assets
        val listAssets = assets.list("") ?: return ""
        if (listAssets.isEmpty()) return ""

        val randomIndex = Random.nextInt(listAssets.size)
        val randomFile = listAssets[randomIndex]


        val inputStream = assets.open(randomFile)
        val reader = BufferedReader(InputStreamReader(inputStream))


        val lines = reader.use { it.readLines() }


        if (lines.isEmpty()) return ""


        val randomLineIndex = Random.nextInt(lines.size)
        val randomLine = lines[randomLineIndex]

        return randomLine
    }
}