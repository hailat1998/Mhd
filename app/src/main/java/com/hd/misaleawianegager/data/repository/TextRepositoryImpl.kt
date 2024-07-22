package com.hd.misaleawianegager.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MisaleWorker
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRepositoryImpl @Inject constructor(private val assetsTextService: AssetsTextService ,
                                            private val fileService: FileService ,
                                            private val workManager: WorkManager,
                                             @ApplicationContext private val context: Context):
    TextRepository {

    override fun readTextAsset(context: Context, type: String): Flow<Resources<String>> {
        Log.i("FROM REPO", "reading")
        return flow{
       assetsTextService.readTexts(context , type).collect{data ->
           if(data == null){
               emit(Resources.Loading(true))
           }else{
               emit(Resources.Success(data))
           }
       }
        }
    }

    override fun search(context: Context, query: String): Flow<Resources<String>> {
       return flow{
           assetsTextService.search(context, query).collect{ result ->
               emit(Resources.Success(result))
           }
       }
    }

    override fun readTextFile(context: Context, type: Int): Flow<Resources<String>> {
    return flow{
        fileService.readTexts(context, type ).collect{ it ->
            emit(Resources.Success(it))
        }
    }
    }

    override fun writeTextFile(context: Context, type: Int, text: String): Boolean {
        return fileService.writeTexts(context, type, text)
    }

    override fun enqueueWork(): Flow<String> {

        val periodicWorkRequest = PeriodicWorkRequestBuilder<MisaleWorker>(30 , TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "Daily Quote",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
       return  workManager.getWorkInfosForUniqueWorkLiveData("Daily Quote").asFlow().map{
            if(it.first().state == WorkInfo.State.SUCCEEDED){
                "SUCCESS"
            }else if(it.first().state == WorkInfo.State.RUNNING){
                "RUNNING"
            } else {
                "FAILED"
            }
        }
        println("work enqueued")
    }
}