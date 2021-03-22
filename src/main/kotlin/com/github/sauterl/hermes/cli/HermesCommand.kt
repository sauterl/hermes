package com.github.sauterl.hermes.cli

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

class HermesCommand : NoOpCliktCommand(name="hermes", help="The hermes system") {

    init {
        subcommands(SendCommand(), com.github.sauterl.hermes.cli.BatchedSendCommand())
    }
}