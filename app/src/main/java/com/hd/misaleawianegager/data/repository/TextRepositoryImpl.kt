package com.hd.misaleawianegager.data.repository

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MisaleWorker
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRepositoryImpl @Inject constructor(private val assetsTextService: AssetsTextService ,
                                            private val fileService: FileService ,
                                             private val workerTextService: WorkerTextService,
                                             @ApplicationContext private val context: Context):
    TextRepository {

    override fun readTextAsset(context: Context, type: Int, dispatcher: CoroutineDispatcher): Flow<Resources<String>> {
        return flow{
       assetsTextService.readTexts(context , type, dispatcher).collect{data ->
           if(data == null){
               emit(Resources.Loading(true))
           }else{
               emit(Resources.Success(data))
           }
       }
        }
    }

    override fun readTextFile(context: Context, type: Int): Flow<Resources<String>> {
    return flow{

    }
    }

    override fun writeTextFile(context: Context, type: Int, text: String): Boolean {
     //TODO : here in the code
        return true
    }

    override fun enqueueWork() {

        val workManager = WorkManager.getInstance(context)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MisaleWorker>(24, TimeUnit.HOURS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "MyPeriodicWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }
}