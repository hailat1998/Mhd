package com.hd.misaleawianegager.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MisaleWorker
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val WORK_NAME = "DailyQuoteMisale"

@Singleton
class TextRepositoryImpl @Inject constructor(private val assetsTextService: AssetsTextService ,
                                            private val fileService: FileService ,
                                            private val workManager: WorkManager ):
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


        val periodicWorkRequest = PeriodicWorkRequestBuilder<MisaleWorker>(20 , TimeUnit.MINUTES)
            .build()
        Log.i("FROM REPO" , "working on work")
        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )


        return workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME).asFlow().map { workInfos ->
            if (workInfos.isNotEmpty()) {
                when (workInfos.first().state) {
                    WorkInfo.State.SUCCEEDED -> "SUCCESS"
                    WorkInfo.State.RUNNING -> "RUNNING"
                    WorkInfo.State.ENQUEUED -> "ENQUEUED"
                    WorkInfo.State.FAILED -> "FAILED"
                    WorkInfo.State.BLOCKED -> "BLOCKED"
                    WorkInfo.State.CANCELLED -> "CANCELLED"
                    else -> "UNKNOWN"
                }
            } else {
                "NO_WORK"
            }
        }.catch { e ->
            emit("ERROR: ${e.message}")
        }
    }
}