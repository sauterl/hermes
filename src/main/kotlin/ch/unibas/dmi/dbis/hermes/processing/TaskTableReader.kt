package ch.unibas.dmi.dbis.hermes.processing

import ch.unibas.dmi.dbis.hermes.model.TaskTable
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

object TaskTableReader {

    fun read(file: File): TaskTable {
        val table = TaskTable()
        csvReader().readAllWithHeader(file).forEachIndexed { index, map ->
            // KEY and TO are mandatory
            if(map.containsKey(TaskTable.KEY_COl_KEY) && map.containsKey(TaskTable.TO_COL_KEY)){
                table.addEntry(map[TaskTable.KEY_COl_KEY]!!, map)
            }else{
                throw RuntimeException("Invalid format on line $index")
            }
        }
        return table
    }
}