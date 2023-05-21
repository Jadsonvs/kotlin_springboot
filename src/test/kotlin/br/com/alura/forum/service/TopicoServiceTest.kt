package br.com.alura.forum.service

import br.com.alura.forum.converter.TopicoFormConverter
import br.com.alura.forum.converter.TopicoViewConverter
import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {//Cria um objeto fictício (Mock) e já defini a simulação do Mock
        every { findByCursoNome(any(), any()) } returns topicos
    }

    val topicoViewConverter: TopicoViewConverter = mockk()//Cria um objeto fictício (Mock)
    val topicoFormConverter: TopicoFormConverter = mockk()

    val topicoService = TopicoService(topicoRepository, topicoViewConverter, topicoFormConverter)
    //Criado uma instãncia de TopicoService com 3 Mocks para o funcionamento

    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        //Given
        every { topicoViewConverter.converterFrom(any()) } returns TopicoViewTest.build()

        //When
        topicoService.listar(nomeCurso = "Kotlin", paginacao = paginacao)

        //Then
        verify(exactly = 1) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 0) {topicoRepository.findAll(paginacao)}
        verify(exactly = 1) {topicoViewConverter.converterFrom(any())}
   }


}