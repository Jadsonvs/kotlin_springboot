package br.com.alura.forum.controller

import br.com.alura.forum.dto.*
import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.TopicoService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/topicos")
class TopicoController(
    private val service: TopicoService
) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
        ): Page<TopicoViewDTO>{
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoViewDTO{
        return service.buscarPorId(id)
    }

    @GetMapping("/topico-nao-respondido")
    fun topicoNaoRespondido(): List<TopicoNaoRespondidoViewDTO> {
       return service.topicoNaoRespondido()
    }

    @PostMapping
    @Transactional
    fun cadastrarTopico(
        @RequestBody @Valid topicoDTO: TopicoFormDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoViewDTO>{
        val topicoView = service.cadastrarTopico(topicoDTO)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid atualizacaoTopicoDTO: AtualizacaoTopicoFormDTO): ResponseEntity<TopicoViewDTO>{
        val topicoView = service.atualizar(atualizacaoTopicoDTO)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable id: Long){
        return service.deletar(id)
    }

}