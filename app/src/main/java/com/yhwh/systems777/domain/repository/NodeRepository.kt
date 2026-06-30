package com.yhwh.systems777.domain.repository

import com.yhwh.systems777.data.local.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

interface NodeRepository {
    fun getAllNodes(): Flow<List<NodeEntity>>
    suspend fun insertNode(node: NodeEntity)
    suspend fun updateNode(node: NodeEntity)
    suspend fun deleteNode(node: NodeEntity)
}
