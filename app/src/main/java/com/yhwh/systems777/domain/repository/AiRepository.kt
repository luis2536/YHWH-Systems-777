package com.yhwh.systems777.domain.repository

interface AiRepository {
    suspend fun analyzeSystemState(logs: String, query: String): String
}
