package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.AddressBook
import com.github.sauterl.hermes.model.Mail
import com.github.sauterl.hermes.model.ReplacementTable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class MailProcessorTest{

    /*
    Subject:Test Email Subject $name
From:me@mail.com
To:alice@mail.com

Hey $name
Did you know $value
Best

     */

    @Test
    fun testProcessEmail(){
        val emptyAB = AddressBook()
        val replacementTable = ReplacementTable()
        replacementTable.add("key",
            mapOf(
                "\$name" to "Alice",
                "\$value" to "how cool Kotlin is?"))

        val expected = Mail(
            to=listOf("alice@mail.com"),
            from="me@mail.com",
            subject = "Test Email Subject Alice",
            body="""
                Hey Alice
                Did you know how cool Kotlin is?
                Best
            """.trimIndent()
        )

        val templ = MailTemplateReader.readTemplate(File("src/test/resources/mailToParse.txt"))

        val parsed = MailProcessor.render("key", templ, replacementTable, emptyAB)

        assertEquals(expected, parsed)

    }
}