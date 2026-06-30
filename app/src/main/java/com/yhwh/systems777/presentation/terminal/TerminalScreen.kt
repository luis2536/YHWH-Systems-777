package com.yhwh.systems777.presentation.terminal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "YHWH SYSTEMS 777 TERMINAL v2.0-PRO",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Text(
            text = "AI_CORE_STATUS: ONLINE | MCP_ENABLED",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(uiState.logs, key = { it.id }) { log ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(500)) + slideInVertically()
                ) {
                    val time = formatter.format(Date(log.timestamp))
                    val logColor = when {
                        log.message.startsWith(">> [GEMINI") || log.message.startsWith(">> [AI-") -> MaterialTheme.colorScheme.secondary
                        log.severity == "ERROR" -> MaterialTheme.colorScheme.error
                        log.severity == "WARN" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                        else -> MaterialTheme.colorScheme.primary
                    }
                    
                    Text(
                        text = "[$time] [${log.severity}] ${log.message}",
                        color = logColor,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            
            if (uiState.logs.isEmpty() && !uiState.isLoading) {
                item {
                    Text(
                        text = "> Waiting for system logs...",
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        if (uiState.isAiThinking) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Input UI para interactuar con la IA
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 90.dp),
            placeholder = { Text("CMD AI Prompt...", color = Color.Gray) },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.sendCommand(inputText)
                    inputText = ""
                }
            ),
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.sendCommand(inputText)
                    inputText = ""
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Command",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}
