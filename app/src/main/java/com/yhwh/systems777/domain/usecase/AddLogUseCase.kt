package com.yhwh.systems777.domain.usecase

import com.yhwh.systems777.data.local.entity.LogEntity
import com.yhwh.systems777.domain.repository.LogRepository
import javax.inject.Inject

class AddLogUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(severity: String, message: String) {
        val log = LogEntity(
            timestamp = System.currentTimeMillis(),
            message = message,
            severity = severity
        )
        logRepository.insertLog(log)
    }
}
