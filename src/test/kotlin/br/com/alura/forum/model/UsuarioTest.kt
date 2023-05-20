package br.com.alura.forum.model

object UsuarioTest {
    fun build() = Usuario(
        id = 1,
        nome = "Jadson Viana",
        email = "jadson@email.com",
        password = "123456"
    )
}
