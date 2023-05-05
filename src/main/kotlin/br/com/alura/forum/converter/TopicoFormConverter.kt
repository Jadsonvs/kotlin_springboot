package br.com.alura.forum.converter

import br.com.alura.forum.dto.TopicoFormDTO

import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.CursoService
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormConverter(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
): Converter<TopicoFormDTO, Topico> {


    override fun converterFrom(from: TopicoFormDTO): Topico {
        return Topico(
            titulo = from.titulo,
            mensagem = from.mensagem,
            curso = cursoService.buscarCursoPorId(from.idCurso),
            autor = usuarioService.buscarUsuarioPorId(from.idAutor)
        )
    }


}