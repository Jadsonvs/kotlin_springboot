package br.com.alura.forum.converter

import br.com.alura.forum.dto.RespostaFormDTO
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.RespostaService
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaFormConverter(
    val usuarioService: UsuarioService,
    val topicoService: TopicoService
): Converter<RespostaFormDTO, Resposta>{
    override fun converterFrom(from: RespostaFormDTO): Resposta {

        return Resposta(
            mensagem = from.mensagem,
            autor = usuarioService.buscarUsuarioPorId(from.idAutor),
            topico = topicoService.buscarPorIdTopico(from.idTopico),
            solucao = from.solucao
        )
    }
}