package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoRespostaFormDTO
import br.com.alura.forum.dto.RespostaFormDTO
import br.com.alura.forum.dto.RespostaViewDTO
import br.com.alura.forum.service.RespostaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/respostas")
class RespostaController(
    val respostaService: RespostaService
) {
    
    @GetMapping
    fun listar(
        @RequestParam(required = false) tituloTopico: String?,
        @PageableDefault() paginacao: Pageable
        ): Page<RespostaViewDTO>{
        return respostaService.listar(tituloTopico, paginacao)
    }

    @GetMapping("/resposta-por-topico")
    fun listarRespostasPorTopico(@RequestParam tituloTopico: String): List<RespostaViewDTO> {
        return respostaService.listarRespostasPorTopico(tituloTopico)
    }

    @PostMapping
    @Transactional
    fun cadastrar(
        @RequestBody @Valid respostaFormDTO: RespostaFormDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<RespostaViewDTO>{
        val respostaView =  respostaService.cadastrar(respostaFormDTO)
        val uri = uriBuilder.path("respostas/${respostaView.id}").build().toUri()
       return ResponseEntity.created(uri).body(respostaView)
    }

    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid formAtualizacao: AtualizacaoRespostaFormDTO): ResponseEntity<RespostaViewDTO>{
        val respostraView = respostaService.atualizar(formAtualizacao)
       return ResponseEntity.ok(respostraView)

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable id: Long){
        respostaService.deletar(id)
    }

}