package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileReader

class AssetsTextServiceImp : AssetsTextService {
    override fun readTexts(context: Context, type : Int, dispatcher: CoroutineDispatcher): Flow<String?> {

        return flow {
            val assets = context.assets
            val list = assets.list(type.toString())
            if (list != null) {
                for (file in list) {
                    val reader = BufferedReader(withContext(dispatcher) {
                        FileReader(file)
                    })
                    var line: String?


                    while (reader.readLine().also { line = it } != null) {

                       line?.let { emit(it) }
                    }
                }

            }

        }
            .flowOn(dispatcher)
    }

    override fun readRandomTexts(context: Context): Flow<String> {
         return flow{

         }
    }

}