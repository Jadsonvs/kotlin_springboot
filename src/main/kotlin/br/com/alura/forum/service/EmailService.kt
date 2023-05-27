package br.com.alura.forum.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val emailSender: JavaMailSender
) {

    fun notification(emailAutor: String) {
        val message = SimpleMailMessage()

        message.apply {
            subject = "[Alura] Resposta recebida!"
            text = "Olá, o seu tópico foi respondido. Vamos lá conferir?"
            setTo(emailAutor)
        }

        emailSender.send(message)

    }

}