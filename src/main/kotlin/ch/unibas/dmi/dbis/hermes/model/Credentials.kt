package ch.unibas.dmi.dbis.hermes.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Credentials(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val ssl: Boolean
){

    companion object{
        fun fromFile(file: File): Credentials {
            return Json.decodeFromString(file.readText())
        }
    }
}