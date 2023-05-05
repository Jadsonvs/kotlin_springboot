package br.com.alura.forum.converter

import br.com.alura.forum.dto.RespostaViewDTO
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaViewConverter(
    val usuarioService: UsuarioService,
    val topicoService: TopicoService
): Converter<Resposta, RespostaViewDTO> {
    override fun converterFrom(from: Resposta): RespostaViewDTO {

        return RespostaViewDTO(
            id = from.id,
            mensagem = from.mensagem,
            dataCriacao = from.dataCriacao,
            nomeAutor = from.autor.nome,
            tituloTopico = from.topico.titulo,
            solucao = from.solucao
        )
    }
}