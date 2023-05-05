package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Curso
import br.com.alura.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val repository: CursoRepository,
    val notFoundMessage: String? = "Curso não encontrado!"
) {

    fun buscarCursoPorId(id: Long): Curso{
       return repository.findById(id)
           .orElseThrow{ NotFoundException(notFoundMessage) }
    }

}
