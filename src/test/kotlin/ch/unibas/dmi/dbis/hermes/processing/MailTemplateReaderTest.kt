package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.MailTemplate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.RuntimeException

class MailTemplateReaderTest{
    @Test
    fun testReadMinimalMail(){
        val expected = MailTemplate(
            "Test Email Subject",
        "alice@mail.com",
            "me@mail.com",
        body="""
            Hey Alice
            It's me,
            Best
        """.trimIndent())

        val read = MailTemplateReader.readTemplate(File("src/test/resources/minimalMail.txt"))

        assertEquals(expected, read)
    }

    @Test
    fun testNotEnoughHeadersMail(){
        try{
            MailTemplateReader.readTemplate(File("src/test/resources/tooFewHeaders.txt"))
        }catch (e: RuntimeException){
            assertTrue(e.message!!.contains("Invalid format"))
        }
    }
}