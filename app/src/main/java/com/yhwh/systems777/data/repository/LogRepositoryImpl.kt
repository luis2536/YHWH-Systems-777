package com.yhwh.systems777.data.repository

import com.yhwh.systems777.data.local.dao.LogDao
import com.yhwh.systems777.data.local.entity.LogEntity
import com.yhwh.systems777.domain.repository.LogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogRepositoryImpl @Inject constructor(
    private val logDao: LogDao
) : LogRepository {

    override fun getLogs(): Flow<List<LogEntity>> {
        return logDao.getLogs()
    }

    override suspend fun insertLog(log: LogEntity) {
        logDao.insertLog(log)
    }
}
