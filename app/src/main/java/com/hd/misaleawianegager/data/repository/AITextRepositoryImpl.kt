package com.hd.misaleawianegager.data.repository

import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.domain.repository.AITextRepository
import com.hd.misaleawianegager.utils.CacheManager
import com.hd.misaleawianegager.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AITextRepositoryImpl @Inject constructor(private val api: ProverbApi,
                                               private val cacheManager: CacheManager
): AITextRepository {

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
}