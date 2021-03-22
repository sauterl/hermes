package com.github.sauterl.hermes.processing

import com.github.sauterl.hermes.model.*
object MailProcessor {

    fun render(
        key: String?,
        template: MailTemplate,
        dictionary: ReplacementTable,
        addresses: AddressBook
    ): Mail {
        val mail = Mail(
            to= parseAddress(template.to, addresses),
            from=template.from,
            subject = parseText(key, template.subject, dictionary),
            body= parseText(key, template.body, dictionary)
        )

        // TODO Implement CC / BCC

        return mail
    }

    fun renderBatched(tasks: TaskTable, template:MailTemplate, dictionary: ReplacementTable, addresses: AddressBook): List<Mail> {
        return tasks.all().map { (key, map) ->
            val subject = parseText(key, template.subject, dictionary)
            val body = parseText(key, template.body, dictionary)

            val to = parseAddress(map[TaskTable.TO_COL_KEY]!!, addresses)
            val cc = if(map.containsKey(TaskTable.CC_COL_KEY)){
                parseAddress(map[TaskTable.CC_COL_KEY]!!, addresses)
            }else{
                emptyList()
            }
            val bcc = if(map.containsKey(TaskTable.BCC_COL_KEY)){
                parseAddress(map[TaskTable.BCC_COL_KEY]!!, addresses)
            }else{
                emptyList()
            }

            return@map Mail(to,template.from,subject,body, cc, bcc)
        }
    }


    private fun parseAddress(template:String, book: AddressBook): List<String> {
        return if(template.contains(AddressBook.SEPARATOR)){
            template.split(AddressBook.SEPARATOR)
        }else{
            if(book.entries.isNotEmpty() && !book.get(template).isNullOrEmpty()){
                book.get(template)!!
            }else{
                listOf(template)
            }
        }
    }

    private fun parseText(key: String?,str:String, dictionary: ReplacementTable): String {
        var working = str
        if(key == null){
            dictionary.allEntries().forEach { it.forEach { (k, v) -> working = working.replace(k,v) } }
        }else{
            dictionary.getAll(key)?.forEach { (k, v) -> working = working.replace(k,v) }
        }
        return working
    }
}