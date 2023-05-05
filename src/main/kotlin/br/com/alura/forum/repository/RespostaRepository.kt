package br.com.alura.forum.repository

import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RespostaRepository: JpaRepository<Resposta, Long> {

    fun findByTopicoTituloContainingIgnoreCase(tituloTopico: String?, paginacao: Pageable): Page<Resposta>

}