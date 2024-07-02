package com.hd.misaleawianegager.di

import com.hd.misaleawianegager.data.local.FileServiceImp
import com.hd.misaleawianegager.domain.local.FileService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FileServiceModule {
     @Binds
     @Singleton
    abstract fun bindFileService(fileServiceImp: FileServiceImp): FileService
}