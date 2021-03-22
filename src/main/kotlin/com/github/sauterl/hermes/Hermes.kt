package com.github.sauterl.hermes

import com.github.sauterl.hermes.cli.HermesCommand
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