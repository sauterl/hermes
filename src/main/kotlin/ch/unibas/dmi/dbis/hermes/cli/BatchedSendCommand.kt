package ch.unibas.dmi.dbis.hermes.cli

import ch.unibas.dmi.dbis.hermes.model.Credentials
import ch.unibas.dmi.dbis.hermes.processing.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int

class BatchedSendCommand : CliktCommand(name = "batch", help = "Send batched emails") {

    val templateFile by option("--mail", "-m", help = "The email template").file(canBeDir = false)
        .required()
    val credFile by option(
        "--credentials",
        "-c",
        help = "The credentials json"
    ).file(canBeDir = false).required()
    val dictFile by option("--dictionary", "-d", help = "The dictionary csv for replacements").file(
        canBeDir = false
    ).required()
    val addressFile by option("--addresses", "-a", help = "The addressbook csv").file().required()
    val taskFile by option("--tasks", "-t", help = "Thhe file with tasks").file().required()

    val delay by option("--delay", "-p", help="A delay in seconds between sending").int().default(2)

    override fun run() {
        val template = MailTemplateReader.readTemplate(templateFile)
        val creds = Credentials.fromFile(credFile)
        val dict = ReplacementTableReader.read(dictFile)
        val addressBook = AddressBookReader.read(addressFile)
        val tasks = TaskTableReader.read(taskFile)
        println("Successfully loaded files")

        val mails = MailProcessor.renderBatched(
            tasks,
            template,
            dict,
            addressBook
        )
        println("Processed all tasks")

        mails.forEachIndexed { index, mail ->
            println("  [Task-${tasks.getKeyByIndex(index)}] Preparing to send")
            MailSender.send(mail, creds)
            println("  [Task-${tasks.getKeyByIndex(index)}] Sent!")
            Thread.sleep((delay * 1000).toLong())
         }
    }
}