package com.tklcraft.twipostmc.command

import org.bukkit.command.CommandSender

object SampleCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = SAMPLE_CMD,
        aliases = setOf("sample", "ex"),
        usage = "Help text description"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        // Write your code
        sender.sendMessage(usage)
    }
}