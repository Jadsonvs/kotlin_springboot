package br.com.alura.forum.dto

import java.time.LocalDateTime

data class RespostaViewDTO(
    val id: Long?,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val nomeAutor: String,
    val tituloTopico: String,
    val solucao: Boolean
)