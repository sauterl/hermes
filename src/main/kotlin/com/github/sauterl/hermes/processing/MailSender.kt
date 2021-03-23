package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.Credentials
import com.github.sauterl.hermes.model.Mail
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

object MailSender {

    fun send(mail: Mail, credentials: Credentials, simulate: Boolean = false){
        val email = SimpleEmail()
        email.setDebug(true)

        email.hostName = credentials.host
        email.setSmtpPort(credentials.port)
        if(credentials.ssl){
            email.sslSmtpPort = credentials.port.toString()
        }
        when(credentials.port){
            465 -> email.isSSLOnConnect = credentials.ssl
            25 -> email.apply {
                isStartTLSEnabled = credentials.ssl
                isStartTLSRequired = credentials.ssl
                isSSLCheckServerIdentity = credentials.ssl
            }
        }
//        email.isStartTLSEnabled = credentials.ssl
//        email.isSSLCheckServerIdentity = true
//        email.isStartTLSRequired = credentials.ssl
        email.setAuthenticator(DefaultAuthenticator(credentials.username, credentials.password))
        email.setFrom(mail.from)
        email.subject = mail.subject
        email.setMsg(mail.body)

        mail.to.forEach { email.addTo(it) }
        mail.cc.forEach { email.addCc(it) }
        mail.bcc.forEach { email.addBcc(it) }
        if(!mail.bcc.contains(mail.from)){
            email.addBcc(mail.from)
        }

        System.setProperty("java.net.preferIPv4Stack", "true")

        if(!simulate){
            email.send()
        }

    }
}