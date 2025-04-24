package com.hd.misaleawianegager.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MyWorkerFactory
import com.hd.misaleawianegager.di.InitializerEntryPoint
import java.util.concurrent.Executors
import javax.inject.Inject

class WorkManagerInitializer : Initializer<WorkManager>, Configuration.Provider {

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() {

            val workerFactory = DelegatingWorkerFactory()

            workerFactory.addFactory(myWorkerFactory)

            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setExecutor(Executors.newFixedThreadPool(5))
                .setMinimumLoggingLevel(Log.INFO)
                .build()
        }

    override fun create(context: Context): WorkManager {

        val entryPoint= InitializerEntryPoint.resolve(context)

        entryPoint.inject(this)

        WorkManager.initialize(context, workManagerConfiguration)

        return WorkManager.getInstance(context)

    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}