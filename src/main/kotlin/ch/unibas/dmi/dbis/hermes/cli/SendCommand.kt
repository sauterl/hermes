package ch.unibas.dmi.dbis.hermes.cli

import ch.unibas.dmi.dbis.hermes.model.AddressBook
import ch.unibas.dmi.dbis.hermes.model.Credentials
import ch.unibas.dmi.dbis.hermes.model.ReplacementTable
import ch.unibas.dmi.dbis.hermes.processing.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file

class SendCommand: CliktCommand(name="send", help="Sends an email") {

    val templateFile by option("--mail", "-m", help="The email template").file(canBeDir = false).required()
    val credFile by option("--credentials", "-c", help="The credentials json").file(canBeDir = false).required()
    val dictFile by option("--dictionary", "-d", help="The dictionary csv for replacements").file(canBeDir = false)
    val addressFile by option("--addresses", "-a", help="The addressbook csv").file()
    val key by option("--key", "-key", help = "The key for the dict")

    override fun run() {
        val template = MailTemplateReader.readTemplate(templateFile)
        val creds = Credentials.fromFile(credFile)

        val dict = if(dictFile != null){
            ReplacementTableReader.read(dictFile!!)
        }else{
            ReplacementTable()
        }
        val addressBook = if(addressFile != null){
            AddressBookReader.read(addressFile!!)
        }else{
            AddressBook()
        }
        println("Successfully loaded files")

        val mail = MailProcessor.render(key, template, dict,addressBook)


        println("Successfully processed email. Will send")

        MailSender.send(mail, creds)

        println("Done")
    }
}