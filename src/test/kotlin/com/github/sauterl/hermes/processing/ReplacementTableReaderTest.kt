package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.ReplacementTable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class ReplacementTableReaderTest{

    /*
    key,$name,$value
    1,Alice,"Quick, catch it"
    2,Bob,444
     */

    @Test
    fun testBasicDictionary(){
        val expected = ReplacementTable()
        expected.add("1", mapOf("\$name" to "Alice", "\$value" to "Quick, catch it"))
        expected.add("2", mapOf("\$name" to "Bob", "\$value" to "444"))

        val read = ReplacementTableReader.read(File("src/test/resources/basicTable.csv"))

        assertEquals("Alice", read.get("1", "\$name"))
        assertEquals("Bob", read.get("2", "\$name"))

        assertEquals(expected.get("1", "\$name"), read.get("1", "\$name"))
        assertEquals(expected.get("1", "\$value"), read.get("1", "\$value"))
        assertEquals(expected.get("2", "\$name"), read.get("2", "\$name"))
        assertEquals(expected.get("2", "\$value"), read.get("2", "\$value"))
    }
}