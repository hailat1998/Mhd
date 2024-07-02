package com.hd.misaleawianegager.data.repository

import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTextRepository(
        impl: TextRepositoryImpl
    ): TextRepository

}