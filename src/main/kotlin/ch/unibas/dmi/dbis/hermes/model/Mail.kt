package ch.unibas.dmi.dbis.hermes.model

data class Mail(
    val to: List<String>,
    val from: String,
    val subject: String,
    val body: String,
    val cc: List<String> = emptyList(),
    val bcc: List<String> = emptyList()
)