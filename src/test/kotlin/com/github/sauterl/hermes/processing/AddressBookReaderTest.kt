package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.AddressBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class AddressBookReaderTest{

    /*
    key,email
1,alice@mail.com
2,bob@mail.com
1,alice.office@mail.com
3,alan@turing.com
     */
    @Test
    fun testAddressBookBasic(){
        val expected = AddressBook()
        expected.addSingles(listOf(
            "1" to "alice@mail.com",
            "2" to "bob@mail.com",
            "1" to "alice.office@mail.com",
            "3" to "alan@turing.com"
        ))

        val read = AddressBookReader.read(File("src/test/resources/addressBookBasic.csv"))


        assertEquals(expected.get("1"), read.get("1"))
        assertEquals(expected.get("2"), read.get("2"))
        assertEquals(expected.get("3"), read.get("3"))
    }
}