package br.com.alura.forum.service

import br.com.alura.forum.converter.TopicoFormConverter
import br.com.alura.forum.converter.TopicoViewConverter
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val topicoRepository: TopicoRepository = mockk {//Cria um objeto fictício (Mock) e já defini a simulação do Mock
        every { findByCursoNome(any(), any()) } returns topicos
    }

    val topicoViewConverter: TopicoViewConverter = mockk()//Cria um objeto fictício (Mock)
    val topicoFormConverter: TopicoFormConverter = mockk()

    val topicoService = TopicoService(topicoRepository, topicoViewConverter, topicoFormConverter)
    //Criado uma instãncia de TopicoService com 3 Mocks para o funcionamento


}