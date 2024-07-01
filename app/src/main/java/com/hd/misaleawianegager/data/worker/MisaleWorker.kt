package com.hd.misaleawianegager.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hd.misaleawianegager.data.local.WorkerTextServiceImp
import com.hd.misaleawianegager.domain.local.WorkerTextService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWorker
class MisaleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val workerTextService: WorkerTextService
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val text = workerTextService.readSingleText(applicationContext)

        return Result.success()
    }
}