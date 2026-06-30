package com.yhwh.systems777.domain.repository

import com.yhwh.systems777.data.local.entity.LogEntity
import kotlinx.coroutines.flow.Flow

interface LogRepository {
    fun getLogs(): Flow<List<LogEntity>>
    suspend fun insertLog(log: LogEntity)
}
