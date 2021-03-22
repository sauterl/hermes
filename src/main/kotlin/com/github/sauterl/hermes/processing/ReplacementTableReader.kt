package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.ReplacementTable
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