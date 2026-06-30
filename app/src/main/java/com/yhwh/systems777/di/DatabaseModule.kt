package com.yhwh.systems777.di

import android.content.Context
import com.yhwh.systems777.data.local.dao.LogDao
import com.yhwh.systems777.data.local.dao.NodeDao
import com.yhwh.systems777.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideNodeDao(appDatabase: AppDatabase): NodeDao {
        return appDatabase.nodeDao()
    }

    @Provides
    fun provideLogDao(appDatabase: AppDatabase): LogDao {
        return appDatabase.logDao()
    }
}
