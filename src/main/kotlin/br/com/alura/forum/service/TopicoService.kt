package br.com.alura.forum.service

import br.com.alura.forum.converter.TopicoFormConverter
import br.com.alura.forum.converter.TopicoViewConverter
import br.com.alura.forum.dto.*
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.*
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.io.Serializable
import java.time.LocalDate

@Service
class TopicoService(
        private val repository: TopicoRepository,
        private val topicoViewConverter: TopicoViewConverter,
        private val topicoFormConverter: TopicoFormConverter,
        private val notFoundMessage: String = "Tópico não encontrado!"
){
    @Cacheable(cacheNames = ["topicos"], key = "#root.method.name")
    fun listar(nomeCurso: String?, paginacao: Pageable): Page<TopicoViewDTO> {
        val topicos = nomeCurso?.let {
            repository.findByCursoNome(nomeCurso, paginacao)
        } ?: repository.findAll(paginacao)

        return topicos.map { t ->
            topicoViewConverter.converterFrom(t)
        }
    }
    fun buscarPorIdTopico(id: Long): Topico {
        return repository.findById(id)
                .orElseThrow { NotFoundException(notFoundMessage) }
    }

    fun buscarPorId(id: Long): TopicoViewDTO {
        val topico = repository.findById(id)
                .orElseThrow { NotFoundException(notFoundMessage) }
        return topicoViewConverter.converterFrom(topico)
    }
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrarTopico(topicoFormDTO: TopicoFormDTO): TopicoViewDTO {
        val topico = topicoFormConverter.converterFrom(topicoFormDTO)
        repository.save(topico)
        return topicoViewConverter.converterFrom(topico)
    }

    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(atualizacaoTopicoDTO: AtualizacaoTopicoFormDTO): TopicoViewDTO {
        val topico = repository.findById(atualizacaoTopicoDTO.id)
                .orElseThrow { NotFoundException(notFoundMessage) }

        topico.titulo = atualizacaoTopicoDTO.titulo
        topico.mensagem = atualizacaoTopicoDTO.mensagem
        topico.dataAlteracao = LocalDate.now()

        return topicoViewConverter.converterFrom(topico)
    }
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(id: Long) {
        repository.deleteById(id)

    }

    fun relatorio(): List<TopicoPorCategoriaDTO> {
        return repository.relatorio()
    }

    fun topicoNaoRespondido(): List<TopicoNaoRespondidoViewDTO> {
        val topicos = repository.TopicoNaoRespondido()
        val topNaoResp = topicos.map { topico ->
            TopicoNaoRespondidoViewDTO(
                    autor = topico.autor.nome,
                    titulo = topico.titulo,
                    status = topico.status
            )
        }
        return topNaoResp
    }

}





