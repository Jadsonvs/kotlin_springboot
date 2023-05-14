package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.nio.charset.StandardCharsets
import java.util.*

//Classe responsável por gerar a estrutura do token
@Component
class JWTUtil(private val usuarioService: UsuarioService) {

    private val expiration: Long = 60000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generatedToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String?{
        return Jwts.builder()
                .setSubject(username)                                                      //Definir o subject(geralmente usamos o ID do usuário)
                .claim("role", authorities)                                         //Adiciona uma ou mais afirmação de função(ROLE) ao token de segurança
                .setIssuedAt(Date(System.currentTimeMillis() + expiration))         //Definindo expiração do Token
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), HS256)              //Criando a assinatura com o secret e o algoritmo
                .compact()                                                             //Compacta toda configuração do token e converte para o formato String
    }

    //Método verifica se um token JWT é válido ou não
    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray())).build()
            true
        }catch (e: IllegalArgumentException){
            false
        }
    }

    //decodificar o token JWT e retornar uma instância de Authentication
    fun getAuthentication(jwt: String?): Authentication {

        val username = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray())).build().parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

}