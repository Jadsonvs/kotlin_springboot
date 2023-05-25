package br.com.alura.forum.controller


import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var jwtUtil: JWTUtil
    //Injetando a dependência JWTUtil na classe de teste para pegarmos o método generatedToken()

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    private var token: String? = null

    companion object {
        private const val URI = "/topicos/"
        private const val URI_ID = URI.plus("%s")//URI com id: "/topicos/{id}
    }

    private fun gerarToken(): String? {
        val authorities = mutableListOf(Role(0, "ESCRITA_ESCRITA"))
        return jwtUtil.generatedToken("jadson@email.com", authorities)
    }
    //Gerando um token para autenticação

        @BeforeEach
        fun setup() {
            token = gerarToken()//acrescentando o token gerado na variável token

            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers.springSecurity())
                .build()
        }


    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem autenticacao`() {
        mockMvc.get(URI).andExpect { status { is4xxClientError() } }

    }


        @Test
        fun `deve retornar codigo 200 quando chamar topicos com token`() {
            mockMvc.get(URI) {
                headers { token?.let { this.setBearerAuth(it) } }
            }.andExpect { status { is2xxSuccessful() } }
        }

        @Test
        fun `deve retornar codigo 200 quando chamar topicos por id`() {
            mockMvc.get(URI_ID.format(1)){
                headers { token?.let { this.setBearerAuth(it) } }
            }.andExpect { status { is2xxSuccessful() } }

        }



    }
