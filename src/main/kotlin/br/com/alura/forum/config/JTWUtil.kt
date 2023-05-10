package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JTWUtil {

    private val expiration: Long = 60000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generatedToken(username: String): String?{
        return Jwts.builder()
                .setSubject(username)//Definir o subject(geralmente usamos o ID do usuário)
                .setExpiration(Date(System.currentTimeMillis() + expiration))//Definindo expiração do Token
                .signWith(SignatureAlgorithm.HS256, secret.toByteArray())//Criando a assinatura e definindo o Secret
                .compact()//Compactar o token
    }

}