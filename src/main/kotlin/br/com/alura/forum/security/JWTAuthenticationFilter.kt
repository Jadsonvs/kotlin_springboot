package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    //Método sobrescrito para filtragem e processamento das solicitações..
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization").replace("Bearer", "").trim()

        if (jwtUtil.isValid(token)){
            //Verifica se o token JWT é válido

            val authentication: Authentication = jwtUtil.getAuthentication(token)
            //Verifica se o usuário logado, ainda continua logado

            SecurityContextHolder.getContext().authentication = authentication
            //Defini a autenticação no contexto de segurança
        }
        filterChain.doFilter(request, response)
        //Chama o próximo filtro na cadeia de filtros
    }

}
