package com.yhwh.systems777.presentation.terminal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhwh.systems777.data.local.entity.LogEntity
import com.yhwh.systems777.domain.repository.AiRepository
import com.yhwh.systems777.domain.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TerminalUiState(
    val logs: List<LogEntity> = emptyList(),
    val isLoading: Boolean = true,
    val isAiThinking: Boolean = false
)

@HiltViewModel
class TerminalViewModel @Inject constructor(
    private val logRepository: LogRepository,
    private val aiRepository: AiRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TerminalUiState())
    val uiState: StateFlow<TerminalUiState> = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch(Dispatchers.IO) {
            logRepository.getLogs()
                .catch { 
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .collect { logs ->
                    _uiState.value = _uiState.value.copy(logs = logs, isLoading = false)
                }
        }
    }

    fun sendCommand(command: String) {
        if (command.isBlank()) return
        
        viewModelScope.launch(Dispatchers.IO) {
            // Guardar el comando del usuario como un log
            logRepository.insertLog(
                LogEntity(
                    timestamp = System.currentTimeMillis(),
                    message = "USER_CMD: $command",
                    severity = "INFO"
                )
            )

            _uiState.value = _uiState.value.copy(isAiThinking = true)

            // Extraer contexto de logs recientes para pasarlo a la IA
            val recentLogs = _uiState.value.logs.take(5).joinToString { it.message }
            
            // Consultar a la IA
            val aiResponse = aiRepository.analyzeSystemState(recentLogs, command)
            
            // Guardar respuesta de la IA
            logRepository.insertLog(
                LogEntity(
                    timestamp = System.currentTimeMillis(),
                    message = aiResponse,
                    severity = "WARN" 
                )
            )
            
            _uiState.value = _uiState.value.copy(isAiThinking = false)
        }
    }
}
