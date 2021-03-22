package ch.unibas.dmi.dbis.hermes.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

class HermesCommand : NoOpCliktCommand(name="hermes", help="The hermes system") {

    init {
        subcommands(SendCommand(), BatchedSendCommand())
    }
}