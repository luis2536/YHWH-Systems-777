package com.yhwh.systems777.di

import com.yhwh.systems777.data.repository.AiRepositoryImpl
import com.yhwh.systems777.data.repository.LogRepositoryImpl
import com.yhwh.systems777.data.repository.NodeRepositoryImpl
import com.yhwh.systems777.domain.repository.AiRepository
import com.yhwh.systems777.domain.repository.LogRepository
import com.yhwh.systems777.domain.repository.NodeRepository
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
    abstract fun bindNodeRepository(
        nodeRepositoryImpl: NodeRepositoryImpl
    ): NodeRepository

    @Binds
    @Singleton
    abstract fun bindLogRepository(
        logRepositoryImpl: LogRepositoryImpl
    ): LogRepository
    
    @Binds
    @Singleton
    abstract fun bindAiRepository(
        aiRepositoryImpl: AiRepositoryImpl
    ): AiRepository
}
