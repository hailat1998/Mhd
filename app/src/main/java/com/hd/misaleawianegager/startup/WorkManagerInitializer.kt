package com.hd.misaleawianegager.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MyWorkerFactory
import com.hd.misaleawianegager.di.InitializerEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class WorkManagerInitializer : Initializer<WorkManager>, Configuration.Provider {

    val fixedThreadPool: ExecutorService = Executors.newFixedThreadPool(4)



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
                .setExecutor(Executors.newFixedThreadPool(8))
                .setMinimumLoggingLevel(Log.INFO)
                .build()
        }
}