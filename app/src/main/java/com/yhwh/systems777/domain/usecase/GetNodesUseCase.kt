package com.yhwh.systems777.domain.usecase

import com.yhwh.systems777.data.local.entity.NodeEntity
import com.yhwh.systems777.domain.repository.NodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNodesUseCase @Inject constructor(
    private val nodeRepository: NodeRepository
) {
    operator fun invoke(): Flow<List<NodeEntity>> {
        return nodeRepository.getAllNodes()
    }
}
