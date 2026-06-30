package com.yhwh.systems777.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhwh.systems777.data.local.entity.NodeEntity
import com.yhwh.systems777.domain.usecase.AddLogUseCase
import com.yhwh.systems777.domain.usecase.GetNodesUseCase
import com.yhwh.systems777.domain.usecase.ToggleNodeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNodesUseCase: GetNodesUseCase,
    private val toggleNodeStatusUseCase: ToggleNodeStatusUseCase,
    private val addLogUseCase: AddLogUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeNodes()
    }

    private fun observeNodes() {
        viewModelScope.launch(Dispatchers.IO) {
            getNodesUseCase()
                .onStart { 
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) } 
                }
                .catch { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = "Error al cargar nodos: ${exception.localizedMessage}"
                        ) 
                    }
                    addLogUseCase("ERROR", "Fallo al obtiener nodos: ${exception.message}")
                }
                .collect { nodeList ->
                    _uiState.update { 
                        it.copy(
                            nodes = nodeList,
                            isLoading = false,
                            errorMessage = null
                        ) 
                    }
                }
        }
    }

    fun onToggleNode(node: NodeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                toggleNodeStatusUseCase(node)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error al cambiar estado: ${e.localizedMessage}") }
                addLogUseCase("ERROR", "Fallo al cambiar estado del nodo ${node.nodeName}")
            }
        }
    }

    fun refreshNodes() {
        // En este caso como Flow mantiene una conexion activa y reactiva con Room,
        // esto es mas util para forzar sincronizaciones manuales o limpiar errores.
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        observeNodes()
    }
}
