package com.yhwh.systems777.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yhwh.systems777.data.local.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(node: NodeEntity)

    @Delete
    suspend fun deleteNode(node: NodeEntity)

    @Update
    suspend fun updateNode(node: NodeEntity)

    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<NodeEntity>>
}
