package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.MailTemplate
import java.io.File

object MailTemplateReader {

    fun readTemplate(file: File): MailTemplate {
        var subject =""
        var from=""
        var to=""
        var cc: String? = null
        var bcc: String? = null
        val body= StringBuilder()

        val lines = file.readLines()
        if(lines.size < 5){
            throw RuntimeException("Invalid format: Too few lines")
        }
        var emptyLines = 0
        lines.forEachIndexed { index, it ->
            if(it.isBlank()){
                emptyLines++
                return@forEachIndexed
            }
            if(emptyLines >= 1){
                // Message body: just add it
                body.append(it)
                if(index != lines.size-1){
                    body.append("\n")
                }
                return@forEachIndexed
            }
            val split = it.split(MailTemplate.COLON)
            if(split.size < 2){
                throw RuntimeException("Invalid format on line $index")
            }
            val key =split[0].toLowerCase().trim()
            if(key.startsWith(MailTemplate.SUBJECT_KEY)){
                subject = split[1]
            }else if(key.startsWith(MailTemplate.FROM_KEY)){
                from = split[1].trim()
            }else if(key.startsWith(MailTemplate.TO_KEY)){
                to = split[1].trim()
            }else if(key.startsWith(MailTemplate.CC_KEY)){
                cc = split[1].trim()
            }else if(key.startsWith(MailTemplate.BCC_KEY)){
                bcc = split[1].trim()
            }
        }
        return MailTemplate(subject,to,from,cc,bcc,body.toString())
    }


}