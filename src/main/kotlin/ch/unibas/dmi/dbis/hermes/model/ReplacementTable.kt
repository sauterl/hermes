package ch.unibas.dmi.dbis.hermes.model

class ReplacementTable {

    companion object{
        const val KEY_COL_KEY = "key"
    }

    private val data = mutableMapOf<String, MutableMap<String, String>>()

    fun add(key:String, map:Map<String, String>){
        data[key] = map.toMutableMap()
    }

    fun getAll(key:String): MutableMap<String, String>? {
        return data[key]
    }

    fun get(key: String, placeholder: String): String?{
        return data[key]?.get(placeholder)
    }

    fun allEntries(): List<MutableMap<String, String>> {
        return data.values.toList()
    }
}