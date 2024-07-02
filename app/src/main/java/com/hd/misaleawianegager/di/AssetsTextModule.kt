package com.hd.misaleawianegager.di

import com.hd.misaleawianegager.data.local.AssetsTextServiceImp
import com.hd.misaleawianegager.domain.local.AssetsTextService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AssetsTextModule {

    @Binds
    @Singleton
   abstract fun bindAssetsService(
        assetsTextServiceImp: AssetsTextServiceImp
    ) : AssetsTextService

}