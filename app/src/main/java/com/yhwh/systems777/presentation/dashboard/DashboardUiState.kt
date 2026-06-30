package com.yhwh.systems777.presentation.dashboard

import com.yhwh.systems777.data.local.entity.NodeEntity

data class DashboardUiState(
    val nodes: List<NodeEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
