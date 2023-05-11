package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.authorizeHttpRequests { authorize ->  authorize           //Autoriza requisição htpp. configurar as regras de autorização de acess
            .requestMatchers("/topicos").hasAnyAuthority("LEITURA_ESCRITA")
            .anyRequest().permitAll()
        }.sessionManagement { session -> session                     //gerenciar as sessões as sessões
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Politica de criação das sessões. Não guardar nenhum estado na API
        }.formLogin { form ->  form                                //Formulário de login. Tela do login do Spring
            .disable()
            .httpBasic()//Autenticação básica
        }.logout { logout -> logout
            .deleteCookies("JSESSIONID")
        }
        return http.build()                                      //Vamos retornar via método build(), um objeto do tipo SecurityFilterChain

    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{                    //Defini o tipo de criptografia a ser usada na senha do usuário, antes de armazená-li ou compará-li -> BCryptPasswordEncoder()
        return BCryptPasswordEncoder()
    }
    @Bean
    fun authenticationManager(                               //Maneira usada para registrar e autenticar  as credenciais dos usuários durante o processo de autenticação.
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder,
        userDetailsService: UsuarioService
    ): AuthenticationManager{
      return  http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build()

    }


}