package com.hd.misaleawianegager.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.hd.misaleawianegager.domain.local.WorkerTextService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyWorkerFactory @Inject constructor(
private val workerTextService: WorkerTextService
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            MisaleWorker::class.java.name -> {
               MisaleWorker(appContext, workerParameters, workerTextService)
            }
            else -> null
        }
    }
}