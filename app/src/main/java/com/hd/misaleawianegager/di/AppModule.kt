package com.hd.misaleawianegager.di

import com.hd.misaleawianegager.data.local.AssetsTextServiceImp
import com.hd.misaleawianegager.data.local.FileServiceImp
import com.hd.misaleawianegager.data.local.WorkerTextServiceImp
import com.hd.misaleawianegager.domain.local.AssetsTextService
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.local.WorkerTextService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    fun provideFileService(): FileService = FileServiceImp()

    @Singleton
    @Provides
    fun provideAssetsService(): AssetsTextService = AssetsTextServiceImp()

    @Singleton
    @Provides
    fun provideWorkerTextService(): WorkerTextService = WorkerTextServiceImp()


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