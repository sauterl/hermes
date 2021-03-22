package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.AddressBook
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

object AddressBookReader {

    fun read(file: File): AddressBook {
        val addressBook = AddressBook()
        csvReader().readAllWithHeader(file).forEachIndexed { index, map ->
            if (map.containsKey(AddressBook.KEY_COL_NAME) && map.containsKey(AddressBook.EMAIL_COL_NAME)) {
                // TODO Validate email by regex test
                addressBook.addEntry(
                    map[AddressBook.KEY_COL_NAME]!!,
                    map[AddressBook.EMAIL_COL_NAME]!!
                )
            } else {
                throw RuntimeException("Format error on line $index")
            }
        }
        return addressBook
    }

}