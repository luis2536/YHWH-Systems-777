package com.yhwh.systems777.domain.usecase

import com.yhwh.systems777.data.local.entity.NodeEntity
import com.yhwh.systems777.domain.repository.NodeRepository
import javax.inject.Inject

class ToggleNodeStatusUseCase @Inject constructor(
    private val nodeRepository: NodeRepository,
    private val addLogUseCase: AddLogUseCase
) {
    suspend operator fun invoke(node: NodeEntity) {
        val newStatus = if (node.status == "activo") "inactivo" else "activo"
        val updatedNode = node.copy(
            status = newStatus,
            lastUpdated = System.currentTimeMillis()
        )
        nodeRepository.updateNode(updatedNode)
        
        addLogUseCase(
            severity = "INFO", 
            message = "El nodo '${node.nodeName}' cambió su estado a $newStatus."
        )
    }
}
