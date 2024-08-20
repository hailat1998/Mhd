package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.domain.local.AssetsTextService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class AssetsTextServiceImp @Inject constructor() : AssetsTextService {


    override fun readTexts(context: Context, type : String): Flow<String?> {

        return flow {
            val assets = context.assets
            val assetFile = assets.open("text/$type")
            val reader = BufferedReader(InputStreamReader(assetFile))
            try {
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    emit(line)
                }
            } finally {
                reader.close()
            }
        }.distinctUntilChanged()
    }

    override fun search(context: Context, query: String): Flow<String> {
        return flow {
            val assets = context.assets
            val textList = assets.list("text/")

            textList?.let { list ->
                for (fileName in list) {
                    val reader = BufferedReader(InputStreamReader(assets.open("text/$fileName")))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        if (line!!.contains(query)) {
                            emit(line!!)
                        }
                    }
                    reader.close()
                }
            }
        }.distinctUntilChanged()
    }

    override fun readRandomTexts(context: Context): Flow<String> {
         return flow{

         }
    }

}