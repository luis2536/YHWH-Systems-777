package com.yhwh.systems777.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yhwh.systems777.data.local.entity.LogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogEntity)

    @Query("SELECT * FROM logs ORDER BY timestamp DESC")
    fun getLogs(): Flow<List<LogEntity>>
}
