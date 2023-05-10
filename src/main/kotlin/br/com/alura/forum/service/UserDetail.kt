package br.com.alura.forum.service


import br.com.alura.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

class UserDetail(
    private val usuario: Usuario
): UserDetails {

    override fun getAuthorities() = usuario.role
    override fun getPassword() = usuario.senha
    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}