package com.hd.misaleawianegager.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.hd.misaleawianegager.data.datastore.DataStoreManagerImpl
import com.hd.misaleawianegager.data.local.AssetsTextServiceImp
import com.hd.misaleawianegager.data.local.FileServiceImp
import com.hd.misaleawianegager.data.local.WorkerTextServiceImp
import com.hd.misaleawianegager.data.remote.ProverbApiImpl
import com.hd.misaleawianegager.data.repository.SettingRepositoryImpl
import com.hd.misaleawianegager.data.repository.TextRepositoryImpl
import com.hd.misaleawianegager.domain.datastoremanager.DataStoreManager
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import com.hd.misaleawianegager.domain.remote.ProverbApi
import com.hd.misaleawianegager.domain.repository.SettingRepository
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.CacheManager
import com.hd.misaleawianegager.utils.CacheManagerImp
import com.hd.misaleawianegager.utils.MisaleSpellChecker
import com.hd.misaleawianegager.utils.MisaleSpellCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @IoDispatcher
    @Provides
    fun bindIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Singleton
    @MainDispatcher
    @Provides
    fun bindMainDispatcher(): CoroutineDispatcher = Dispatchers.Main


    @Singleton
    @DefaultDispatcher
    @Provides
    fun bindDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default


    @Singleton
    @Provides
    fun provideFileService(@ApplicationContext context: Context): FileService = FileServiceImp(context)


    @Singleton
    @Provides
    fun provideAssetsService(): AssetsTextService = AssetsTextServiceImp()


//    @Singleton
//    @Provides
//    fun provideWorkerTextService(): WorkerTextService = WorkerTextServiceImp()


    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }


    @Provides
    @Singleton
    fun provideSettingRepo(dataStoreManager: DataStoreManager): SettingRepository = SettingRepositoryImpl(dataStoreManager)


    @Provides
    @Singleton
    fun provideWorkerFactory(workerFactory: HiltWorkerFactory): WorkerFactory =  workerFactory


    @Provides
    @Singleton
    fun provideCacheManager(): CacheManager = CacheManagerImp(hashMapOf())

}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTextRepository(
        impl: TextRepositoryImpl
    ): TextRepository

}

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerServiceModule{

    @Binds
    @Singleton
    abstract fun bindWorkerService(
        impl : WorkerTextServiceImp
    ) : WorkerTextService

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreManagerModule {

    @Binds
    @Singleton
    abstract fun bindDataStoreManager(
        impl: DataStoreManagerImpl
    ): DataStoreManager

}

@Module
@InstallIn(SingletonComponent::class)
abstract class SpellCheckerModule {

    @Binds
    @Singleton
    abstract fun bindMisaleSpellChecker(
        misaleSpellCheckerImpl: MisaleSpellCheckerImpl
    ): MisaleSpellChecker

}