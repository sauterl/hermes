package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.ReplacementTable
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

object ReplacementTableReader {

    fun read(file: File): ReplacementTable {
        val table= ReplacementTable()
        csvReader().readAllWithHeader(file).forEachIndexed { index, map ->
            if(map.containsKey(ReplacementTable.KEY_COL_KEY)){
                table.add(map[ReplacementTable.KEY_COL_KEY]!!, map)
            }else{
                throw RuntimeException("Invalid format on line $index. No key")
            }
        }
        return table
    }
}