package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.twitterInstance
import org.bukkit.command.CommandSender

object RegisterCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = REGISTER_CMD,
        description = "Connect your Twitter account"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        registerPlayerTwitterAccount(sender)
    }

    private fun registerPlayerTwitterAccount(sender: CommandSender) {
        twitterInstance.oAuthAccessToken = null
        val requestToken = twitterInstance.oAuthRequestToken

        sender.sendMessage("Please access this URL: ${requestToken.authorizationURL}")
        sender.sendMessage("After authentication, please enter the PIN code displayed on your browser. /$baseCmd $name")
    }
}