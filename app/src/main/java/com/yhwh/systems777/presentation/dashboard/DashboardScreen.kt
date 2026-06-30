package com.yhwh.systems777.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yhwh.systems777.ui.components.NodeCard

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "SYSTEMS DASHBOARD",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tarjeta de estado de IA
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0F1A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "GEMINI CORE LOGIC [v2.0-PRO]",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "MCP protocols active. Enrutamiento óptimo. Usa la Terminal para interactuar con el core inteligente.",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        Text(
            text = "ACTIVE NODES",
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        if (uiState.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            LazyColumn(
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                items(uiState.nodes) { node ->
                    NodeCard(
                        node = node,
                        onToggle = { viewModel.onToggleNode(it) }
                    )
                }
            }
        }
    }
}
