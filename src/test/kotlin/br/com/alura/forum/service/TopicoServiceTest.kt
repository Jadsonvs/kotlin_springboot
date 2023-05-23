package br.com.alura.forum.service

import br.com.alura.forum.converter.TopicoFormConverter
import br.com.alura.forum.converter.TopicoViewConverter
import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {//Cria um objeto fictício (Mock) e já defini a simulação do Mock
        every { findByCursoNome(any(), any()) } returns topicos
        every{ findAll(paginacao)} returns topicos

    }

    val topicoViewConverter: TopicoViewConverter = mockk{
        every { converterFrom(any()) } returns TopicoViewTest.build()
    }

    val topicoFormConverter: TopicoFormConverter = mockk()//Cria um objeto fictício (Mock)

    val topicoService = TopicoService(topicoRepository, topicoViewConverter, topicoFormConverter)
    //Criado uma instãncia de TopicoService com 3 Mocks para o funcionamento

    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        topicoService.listar(nomeCurso = "Kotlin", paginacao = paginacao)

        verify(exactly = 1) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 1) {topicoViewConverter.converterFrom(any())}
        verify(exactly = 0) {topicoRepository.findAll(paginacao)}
   }

    @Test
    fun `deve listar todos os topicos quando o nome do curso for nulo`() {
        topicoService.listar(null, paginacao)

        verify(exactly = 0) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 1) {topicoViewConverter.converterFrom(any())}
        verify(exactly = 1) {topicoRepository.findAll(paginacao)}
    }

    @Test
    fun `deve lancar not found exception quando topico nao for achado`() {
        every { topicoRepository.findById(any()) } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(1)
        }

        assertThat(atual.message).isEqualTo("Tópico não encontrado!")
    }

}