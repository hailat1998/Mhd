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

        weightedList.shuffle()

        val randomIndex = Random.nextInt(weightedList.size)
        val randomFile = weightedList[randomIndex]


        val inputStream = assets.open("text/$randomFile")
        val reader = BufferedReader(InputStreamReader(inputStream))


        val lines = reader.use { it.readLines() }

        if (lines.isEmpty()) return ""

        val randomLineIndex = Random.nextInt(lines.size)
        val randomLine = lines[randomLineIndex]

        return randomLine
    }

    companion object {
        val weightedList = arrayOf(
            "01Ha.txt",
            "02Le.txt",
            "02Le.txt",
            "02Le.txt",
            "02Le.txt",
            "02Le.txt",
            "02Le.txt",
            "03Ha.txt",
            "04Me.txt",
            "04Me.txt",
            "04Me.txt",
            "04Me.txt",
            "04Me.txt",
            "04Me.txt",
            "05Se.txt",
            "06Re.txt",
            "07Se.txt",
            "07Se.txt",
            "07Se.txt",
            "07Se.txt",
            "07Se.txt",
            "07Se.txt",
            "07Se.txt",
            "08She.txt",
            "09Qe.txt",
            "10Be.txt",
            "10Be.txt",
            "10Be.txt",
            "10Be.txt",
            "10Be.txt",
            "10Be.txt",
            "10Be.txt",
            "11Te.txt",
            "11Te.txt",
            "12Che.txt",
            "13Ha.txt",
            "14Ne.txt",
            "14Ne.txt",
            "14Ne.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "16A.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "17Ke.txt",
            "19We.txt",
            "19We.txt",
            "19We.txt",
            "20A.txt",
            "20A.txt",
            "21Ze.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "23Ye.txt",
            "24De.txt",
            "24De.txt",
            "24De.txt",
            "25Je.txt",
            "26Ge.txt",
            "26Ge.txt",
            "26Ge.txt",
            "27Xe.txt",
            "27Xe.txt",
            "28Che.txt",
            "29Pe.txt",
            "30Xe.txt",
            "31Xe.txt",
            "32Fe.txt",
            "32Fe.txt",
            )

    }
}