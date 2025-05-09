package com.hd.misaleawianegager.data.repository

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.hd.misaleawianegager.data.worker.MisaleWorker
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.CacheManager
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val WORK_NAME = "DailyQuoteMisale"

@Singleton
class TextRepositoryImpl @Inject constructor(private val assetsTextService: AssetsTextService ,
                                             private val fileService: FileService ,
                                             private val workManager: WorkManager ,
                                             private val api: ProverbApi,
                                             private val cacheManager: CacheManager,
                                             private val workerTextService: WorkerTextService,
                                             @ApplicationContext private val context: Context):
    TextRepository {

    override fun readTextAsset(context: Context, type: String): Flow<Resources<String>> {
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
        fileService.readTexts(context, type ).collect{
            emit(Resources.Success(it))
        }
      }
    }

    override fun writeTextFile(context: Context, type: Int, text: String): Boolean {
        return fileService.writeTexts(context, type, text)
    }

    override fun enqueueWork(): Flow<String> {

        val periodicWorkRequest = PeriodicWorkRequestBuilder<MisaleWorker>(6 , TimeUnit.HOURS)
            .build()
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

    override fun getFromNetwork(proverb: String): Flow<Resources<ProverbResponse>> {
        return flow {
            emit(Resources.Loading(true))

            if (cacheManager.contains(proverb)) {
                emit(Resources.Success(cacheManager.get(proverb) as ProverbResponse))
                emit(Resources.Loading(false))
                return@flow
            }

            try {

                val proverbResponse = api.meaning(proverb)

                cacheManager.set(proverbResponse, proverb)
                emit(Resources.Success(proverbResponse))
            } catch (ex: IOException) {
                emit(Resources.Error("Network error: ${ex.localizedMessage}"))
            } catch (ex: Exception) {
                emit(Resources.Error("Error fetching proverb meaning: ${ex.localizedMessage}"))
            } finally {
                emit(Resources.Loading(false))
            }
        }.catch { e ->
            emit(Resources.Error("Unexpected error: ${e.localizedMessage}"))
            emit(Resources.Loading(false))
        }
    }

    override fun laOren2am(latinAmharicText: String): Flow<Resources<String>> {
        return flow {
            emit(Resources.Loading(true))
            try {

                val amharicText = api.enOrLa(latinAmharicText)

                emit(Resources.Success(amharicText))
            } catch (ex: IOException) {
                emit(Resources.Error("Network error: ${ex.localizedMessage}"))
            } catch (ex: Exception) {
                emit(Resources.Error("Error fetching proverb meaning: ${ex.localizedMessage}"))
            } finally {
                emit(Resources.Loading(false))
            }
        }.catch { e ->
            emit(Resources.Error("Unexpected error: ${e.localizedMessage}"))
            emit(Resources.Loading(false))
        }
    }

    override fun en2am(englishText: String): Flow<Resources<String>> {
        return flow {

            emit(Resources.Loading(true))

            try {

                val amharicText = api.en2am(englishText)

                emit(Resources.Success(amharicText))
            } catch (ex: IOException) {
                emit(Resources.Error("Network error: ${ex.localizedMessage}"))
            } catch (ex: Exception) {
                emit(Resources.Error("Error fetching proverb meaning: ${ex.localizedMessage}"))
            } finally {
                emit(Resources.Loading(false))
            }
        }.catch { e ->
            emit(Resources.Error("Unexpected error: ${e.localizedMessage}"))
            emit(Resources.Loading(false))
        }
    }

    override fun readSingle(): Flow<Resources<String>> {
        return flow{
            emit(Resources.Success(workerTextService.readSingleText(context)))
        }
    }
}