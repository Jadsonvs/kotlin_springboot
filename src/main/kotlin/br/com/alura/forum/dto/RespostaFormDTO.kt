package br.com.alura.forum.dto

import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class RespostaFormDTO(

    @field: NotEmpty
    @field: Size(min = 5, max = 200)
    val mensagem: String,

    @field:NotNull
    val idAutor: Long,

    @field:NotNull
    val idTopico: Long,

    @field:NotNull
    val solucao: Boolean
)