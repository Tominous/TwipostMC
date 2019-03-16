package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.twitterInstance
import org.bukkit.command.CommandSender

object PostCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = POST_CMD,
        aliases = setOf(),
        args = "<message>",
        description = "Send a tweet"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        if(args.isEmpty()) {
            sendUsage(sender)
            return
        }
        twitterInstance.updateStatus(args.first())
    }
}