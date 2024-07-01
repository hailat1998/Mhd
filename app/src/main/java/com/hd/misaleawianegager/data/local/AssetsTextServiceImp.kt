package com.hd.misaleawianegager.data.local

import android.content.Context
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.FileReader

class AssetsTextServiceImp : AssetsTextService {
    override fun readTexts(context: Context, type : Int): Flow<Resources<String?>> {

        return flow {
            val assets = context.assets
            val list = assets.list(type.toString())
            if(list != null){
                for(file in list){
                    val reader = BufferedReader(FileReader(file))
                    var line: String?


                    while (reader.readLine().also { line = it } != null) {

                        Resources.Success(line)?.let { emit(it) }
                    }
                }

            }

        }
    }

    override fun readRandomTexts(context: Context): Flow<Resources<String>> {
         return flow{

         }
    }

}