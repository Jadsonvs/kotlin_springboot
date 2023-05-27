package br.com.alura.forum.service

import br.com.alura.forum.converter.RespostaFormConverter
import br.com.alura.forum.converter.RespostaViewConverter
import br.com.alura.forum.dto.AtualizacaoRespostaFormDTO
import br.com.alura.forum.dto.RespostaFormDTO
import br.com.alura.forum.dto.RespostaViewDTO
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.repository.RespostaRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RespostaService(
    private val respostaFormConverter: RespostaFormConverter,
    private val respostaViewConverter: RespostaViewConverter,
    private val repository: RespostaRepository,
    private val notFoundMessage: String? = "Responsta não encontrada!",
    private val emailService: EmailService
) {
    @Cacheable(cacheNames = ["respostas"], key = "#root.method.name")
    fun listar(tituloTopico: String?,paginacao: Pageable): Page<RespostaViewDTO> {
        val respostas = if(tituloTopico == null){
            repository.findAll(paginacao)
        }else{
            repository.findByTopicoTituloContainingIgnoreCase(tituloTopico, paginacao)
        }

        return respostas.map { resposta ->
            respostaViewConverter.converterFrom(resposta)
        }
    }
    fun listarRespostasPorTopico(tituloTopico: String): List<RespostaViewDTO> {
        val respostasEncontrada = repository.findAll().filter { resposta ->
            resposta.topico.toString().startsWith(tituloTopico)
        }
        return respostasEncontrada.map { it ->
            respostaViewConverter.converterFrom(it)
        }
    }
    @CacheEvict(value = ["respostas"], allEntries = true)
    fun cadastrar(respostaFormDTO: RespostaFormDTO): RespostaViewDTO {
        val resposta = respostaFormConverter.converterFrom(respostaFormDTO)
        repository.save(resposta)

        val emailAutor = resposta.autor.email
        emailService.notification(emailAutor)

        return respostaViewConverter.converterFrom(resposta)
    }
    @CacheEvict(value = ["respostas"], allEntries = true)
    fun atualizar(formAtualizacao: AtualizacaoRespostaFormDTO): RespostaViewDTO {
        val resposta = repository.findById(formAtualizacao.id)
            .orElseThrow { NotFoundException(notFoundMessage) }

        val respostaAtualizada = Resposta(
            id = formAtualizacao.id,
            mensagem = formAtualizacao.mensagem,
            dataCriacao = LocalDateTime.now(),
            autor = resposta.autor,
            topico = resposta.topico,
            solucao = formAtualizacao.solucao
        )

        return respostaViewConverter.converterFrom(respostaAtualizada)
    }
    @CacheEvict(value = ["respostas"], allEntries = true)
    fun deletar(id: Long) {
        repository.deleteById(id)
    }

}