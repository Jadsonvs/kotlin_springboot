package br.com.alura.forum.service

import br.com.alura.forum.converter.RespostaFormConverter
import br.com.alura.forum.converter.RespostaViewConverter
import br.com.alura.forum.dto.AtualizacaoRespostaFormDTO
import br.com.alura.forum.dto.RespostaFormDTO
import br.com.alura.forum.dto.RespostaViewDTO
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.RespostaRepository
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RespostaService(
    val respostaFormConverter: RespostaFormConverter,
    val respostaViewConverter: RespostaViewConverter,
    val repository: RespostaRepository,
    val notFoundMessage: String? = "Responsta não encontrada!"
) {

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

    fun cadastrar(respostaFormDTO: RespostaFormDTO): RespostaViewDTO {
        val resposta = respostaFormConverter.converterFrom(respostaFormDTO)
        repository.save(resposta)

        return respostaViewConverter.converterFrom(resposta)
    }

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

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

}