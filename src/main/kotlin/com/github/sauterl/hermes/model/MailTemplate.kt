package com.github.sauterl.hermes.model

data class MailTemplate(
    val subject: String,
    val to: String,
    val from: String,
    val cc: String? = null,
    val bcc: String? = null,
    val body: String
){

    companion object{
        const val COLON = ":"
        const val SUBJECT_KEY = "subject"
        const val TO_KEY = "to"
        const val FROM_KEY = "from"
        const val CC_KEY = "cc"
        const val BCC_KEY = "bcc"
    }

}
