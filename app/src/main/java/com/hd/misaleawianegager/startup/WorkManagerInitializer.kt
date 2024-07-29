package com.hd.misaleawianegager.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MyWorkerFactory
import com.hd.misaleawianegager.di.InitializerEntryPoint
import javax.inject.Inject

class WorkManagerInitializer : Initializer<WorkManager>, Configuration.Provider {

//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory
//
//    @Inject
//    private lateinit var workerTextService: WorkerTextService

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    override fun create(context: Context): WorkManager {
        Log.d("WorkManagerInitializer", "Initializing WorkManager")
          val entryPoint= InitializerEntryPoint.resolve(context)
           entryPoint.inject(this)
//       workerTextService = entryPoint.getWorkerTextService()
        WorkManager.initialize(context, workManagerConfiguration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }

    override val workManagerConfiguration: Configuration
        get() {
            val workerFactory = DelegatingWorkerFactory()
            workerFactory.addFactory(myWorkerFactory)
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }
}