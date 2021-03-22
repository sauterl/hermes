package ch.unibas.dmi.dbis.hermes

import ch.unibas.dmi.dbis.hermes.cli.HermesCommand
import com.github.ajalt.clikt.core.PrintHelpMessage

object Hermes {

    @JvmStatic
    fun main(args: Array<String>) {
        val clikt = HermesCommand()
        try {
            clikt.parse(args)
        }catch(e:PrintHelpMessage){
            println(clikt.getFormattedHelp())
        }
    }
}