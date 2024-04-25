package com.example.mbapp_androidapp.common.classes

import android.R
import android.util.Log
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class MailSender {

    private val mailName = "minibar.notificaciones@gmail.com"
    private val password = "ebex brrh uyui emiw"
    private val tag = "MailSender"

    companion object{
        @Volatile private var INSTANCE: MailSender? = null
        fun getMailSender(): MailSender {
            return INSTANCE ?: synchronized(this) {
                val instance = MailSender()
                INSTANCE = instance
                return instance
            }
        }
    }

    fun send(message:String,subject:String,destMail:String){
        Thread {
            Log.d(tag, "Starting sending")
            val props = Properties()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.port"] = "587"

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(
                        mailName,
                        password
                    )
                }
            })
            try {
                val mimeMessage = MimeMessage(session)
                mimeMessage.setFrom(InternetAddress(mailName))
                mimeMessage.setRecipient(MimeMessage.RecipientType.TO, InternetAddress(destMail))
                mimeMessage.subject = subject
                mimeMessage.setText(message)
                Transport.send(mimeMessage)
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendTempMessage(temp:Double){
        MailSender.getMailSender().send(
            message="*Aviso temperatura anómala(${temp}):*\n" +
                    "El minibar cuenta con una diferencia de temperatura\n" +
                    "significativa entre los sensores interiores y el sensor de la puerta.\n" +
                    "Atender cuanto antes la posible fuga de frío.\n" +
                    "-----------------------------",
            subject="Alerta minibar (${System.barId})",
            destMail = Employee.getInstance().getAdminEmail()
        )
    }
}