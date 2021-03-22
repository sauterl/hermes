package com.github.sauterl.hermes.model

class AddressBook {

    companion object{
        const val KEY_COL_NAME = "key"
        const val EMAIL_COL_NAME = "email"
        const val SEPARATOR = ";"
    }

    val entries = mutableMapOf<String, MutableList<String>>()

    fun addEntry(key:String, addresses:List<String>){
        if(entries.containsKey(key)){
            entries[key]!!.addAll(addresses)
        }else{
            entries[key] = mutableListOf()
            entries[key]!!.addAll(addresses)
        }
    }


    fun addSingles(list: List<Pair<String, String>>){
        list.forEach { addEntry(it.first, it.second) }
    }

    fun addEntry(key: String, addressee: String){
        if(entries.containsKey(key)){
            entries[key]!!.add(addressee)
        }else{
            entries[key] = mutableListOf(addressee)
        }
    }

    fun get(key: String): List<String>?{
        return entries[key]
    }




}