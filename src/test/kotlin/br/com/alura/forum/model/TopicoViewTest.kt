package br.com.alura.forum.model

import br.com.alura.forum.dto.TopicoViewDTO
import java.time.LocalDate
import java.time.LocalDateTime

object TopicoViewTest {
    fun build() = TopicoViewDTO(
        id = 1,
        titulo = "Kotlin Básico",
        mensagem = "Aprendendo kotlin básico",
        status = StatusTopico.NAO_RESPONDIDO,
        dataCriacao = LocalDateTime.now(),
        dataAlteracao = LocalDate.now()
    )
}