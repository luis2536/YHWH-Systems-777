package com.yhwh.systems777.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.yhwh.systems777.domain.repository.AiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiRepositoryImpl @Inject constructor() : AiRepository {
    
    // Poner tu API Key real de Gemini aquí para habilitar IA pura.
    // Si está vacía, usará el modo simulador (MCP Logic).
    private val GEMINI_API_KEY = "" 

    override suspend fun analyzeSystemState(logs: String, query: String): String {
        if (GEMINI_API_KEY.isBlank()) {
            // MCP Simulation Mode (Offline/No-Key Fallback)
            return ">> [AI-MCP-SIMULATION]: Análisis completado.\nQuery: $query\nEstado: Óptimo. Algoritmos locales predicen estabilidad en la red YHWH al 99.9%."
        }

        return try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = GEMINI_API_KEY
            )
            val prompt = "Actúa como una IA Core de un sistema Cyberpunk llamado YHWH 777. Responde breve, sarcástico pero útil. Analiza la petición del usuario: '$query'. Contexto reciente de logs: '$logs'"
            val response = generativeModel.generateContent(prompt)
            ">> [GEMINI-AI-CORE]: ${response.text}"
        } catch (e: Exception) {
            ">> [SYS-ERROR]: Fallo en la red neuronal - ${e.message}"
        }
    }
}
