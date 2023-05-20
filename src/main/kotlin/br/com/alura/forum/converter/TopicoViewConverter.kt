package br.com.alura.forum.converter

import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoViewConverter: Converter<Topico, TopicoViewDTO> {
    override fun converterFrom(from: Topico): TopicoViewDTO {
       return TopicoViewDTO(
            id = from.id,
            titulo = from.titulo,
            mensagem = from.mensagem,
            status = from.status,
            dataCriacao = from.dataCriacao,
           dataAlteracao = from.dataAlteracao
        )
    }

}