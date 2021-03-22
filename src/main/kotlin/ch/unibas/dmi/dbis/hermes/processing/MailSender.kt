package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.Credentials
import ch.unibas.dmi.dbis.hermes.model.Mail
import ch.unibas.dmi.dbis.hermes.model.MailTemplate
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

object MailSender {

    fun send(mail: Mail, credentials: Credentials, simulate: Boolean = false){
        val email = SimpleEmail()
//        email.setDebug(true)

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

        System.setProperty("java.net.preferIPv4Stack", "true")

        if(!simulate){
            email.send()
        }

    }
}