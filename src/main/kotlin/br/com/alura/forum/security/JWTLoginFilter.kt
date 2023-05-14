package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTLoginFilter(
    private val authManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {

    //Cria um token de autenticação com base nas crediciais do usuário, e usa AuthenticationManager para autenticar o token
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val(username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
        val token = UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(token)
    }

    //Gera o nosso token autenticado e devole o mesmo
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        // Gerar token JWT
        val user = (authResult?.principal as UserDetails)

        // Adicionar o token à resposta HTTP ou tomar outras ações necessárias
        val token = jwtUtil.generatedToken(user.username, user.authorities)

        response?.addHeader("Authorization", "Bearer $token")
    }

}
