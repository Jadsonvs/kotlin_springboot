package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    val notFoundMessage: String? = "Usuário não encontrada!"
    ): UserDetailsService {

    fun listar(): List<Usuario>{
        return repository.findAll()
    }

    fun buscarUsuarioPorId(id: Long): Usuario {
        val usuario = repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage)}
        return usuario
    }

    override fun loadUserByUsername(username: String?): UserDetails {
       val usuario = repository.findByEmail(username) ?: throw RuntimeException()
       return UserDetail(usuario)
    }

}
