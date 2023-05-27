package br.com.alura.forum.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val emailSender: JavaMailSender
) {

    fun notification() {
        val message = SimpleMailMessage()

        message.apply {
            subject = ""
            text = ""
            setTo("")
        }

        emailSender.send(message)

    }

}