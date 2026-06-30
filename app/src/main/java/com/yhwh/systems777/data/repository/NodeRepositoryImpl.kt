package com.yhwh.systems777.data.repository

import com.yhwh.systems777.data.local.dao.NodeDao
import com.yhwh.systems777.data.local.entity.NodeEntity
import com.yhwh.systems777.domain.repository.NodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NodeRepositoryImpl @Inject constructor(
    private val nodeDao: NodeDao
) : NodeRepository {

    override fun getAllNodes(): Flow<List<NodeEntity>> {
        return nodeDao.getAllNodes()
    }

    override suspend fun insertNode(node: NodeEntity) {
        nodeDao.insertNode(node)
    }

    override suspend fun updateNode(node: NodeEntity) {
        nodeDao.updateNode(node)
    }

    override suspend fun deleteNode(node: NodeEntity) {
        nodeDao.deleteNode(node)
    }
}
