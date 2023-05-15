package br.com.alura.forum.config

import br.com.alura.forum.security.JWTAuthenticationFilter
import br.com.alura.forum.security.JWTLoginFilter
import br.com.alura.forum.service.UsuarioService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val configuration: AuthenticationConfiguration,
    private val jwtUtil: JWTUtil
) {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        return http
            .headers { it.frameOptions().disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests { authorize ->  authorize                                              //Autoriza requisição htpp. configurar as regras de autorização de acess
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/h2-console").permitAll()
                .requestMatchers("/topicos").hasAuthority("LEITURA_ESCRITA")
                .anyRequest().authenticated()

            }
            .addFilterBefore(JWTLoginFilter(configuration.authenticationManager, jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter::class.java)  //Adiciona o filtro JWT(token) antes do filtro padrão por username e senha
            .addFilterBefore(JWTAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }              //Gerenciar as sessões e a politica de criação das mesmas. Não guardar nenhum estado na API
            .build()                                                                                      //Vamos retornar via método build(), um objeto do tipo SecurityFilterChain

    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{                    //Defini o tipo de criptografia a ser usada na senha do usuário, antes de armazená-la ou compará-la -> BCryptPasswordEncoder()
        return BCryptPasswordEncoder()
    }



//Maneira O authenticationManager usada para registrar e autenticar  as credenciais dos usuários durante o processo de autenticação.





}