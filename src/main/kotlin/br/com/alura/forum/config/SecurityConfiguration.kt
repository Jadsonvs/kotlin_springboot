package br.com.alura.forum.config

import br.com.alura.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
       private val userDetailsService: UserDetailsService,
       private val jwtUtil: JTWUtil,
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.//Se http não for null
            authorizeRequests()?.//Autoriza requisição htpp. configurar as regras de autorização de acesso
//                antMatchers("/topicos")?.hasAnyAuthority("LEITURA_ESCRITA")?.
                antMatchers("/login")?.permitAll()?.
                anyRequest()?.//Quaisquer outras requisições
                authenticated()?.//Devem está autenticadas(exigi login e senha).
                and()
        http?.
            addFilterBefore(
                JWTLoginFilter(authManager = authenticationManager(), jwtUtil = jwtUtil),  UsernamePasswordAuthenticationFilter::javaClass)
        http?.
            sessionManagement()?.//gerenciar as sessões as sessões
                sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.//Politica de criação das sessões. Não guardaremos nenhum estado na API
                and()?.
            formLogin()?.//Formulário de login. Tela do login do Spring
                disable()?.
                httpBasic()//Autenticação básica
    }

    //Método que criptografa a senha
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.
            userDetailsService(userDetailsService)?.//Método que irá comparar as credenciais do nosso usuário, baseada nos detalhes do usuário.
                passwordEncoder(bCryptPasswordEncoder())//Método que codifica e verifica senha do usuário
    }


}