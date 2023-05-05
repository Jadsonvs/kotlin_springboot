package br.com.alura.forum.dto

import java.time.LocalDateTime

data class ErrorViewDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)
