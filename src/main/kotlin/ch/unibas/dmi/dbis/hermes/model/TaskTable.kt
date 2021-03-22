package ch.unibas.dmi.dbis.hermes.model

class TaskTable {

    companion object {
        const val KEY_COl_KEY = "key"
        const val TO_COL_KEY = "to"
        const val CC_COL_KEY = "cc"
        const val BCC_COL_KEY = "bcc"
    }

    private val table = mutableMapOf<String, MutableMap<String, String>>()

    fun addEntry(key:String, map: Map<String, String>){
        table[key] = map.toMutableMap()
    }

    fun get(key:String): MutableMap<String, String>? {
        return table[key]
    }

    fun all():Map<String,Map<String,String>>{
        return table
    }

    fun getKeyByIndex(idx: Int): String {
        return table.keys.toList()[idx]
    }


}