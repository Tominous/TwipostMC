package com.tklcraft.twipostmc.command

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterFactory

object RegisterCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = REGISTER_CMD,
        canRunPlayer = true,
        canRunServer = false,
        description = "Connect user's Twitter account"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        if(sender is Player) registerPlayerTwitterAccount(sender)
    }

    private fun registerPlayerTwitterAccount(player : Player) {
        val twitter = TwitterFactory.getSingleton()
        twitter.oAuthAccessToken = null
        val requestToken = twitter.oAuthRequestToken
        com.tklcraft.twipostmc.requestTokens[player.uniqueId] = requestToken

        player.sendMessage("Please access this URL:")
        player.sendMessage("${ChatColor.AQUA}${requestToken.authorizationURL}")
        player.sendMessage("After authentication, please enter the PIN code displayed on your browser. /$baseCmd $PIN_CMD")
    }
}