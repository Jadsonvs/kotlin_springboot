package br.com.alura.forum.dto

import br.com.alura.forum.model.StatusTopico

data class TopicoNaoRespondidoViewDTO (
        val autor: String,
        val titulo: String,
        val status: StatusTopico
)
