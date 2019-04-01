package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.Globals
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterFactory
import java.util.*

object RegisterCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = REGISTER_CMD,
        canRunPlayer = true,
        canRunServer = true,
        description = "Connect user's Twitter account")
{
    private val twitter = TwitterFactory.getSingleton()

    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        twitter.oAuthAccessToken = null
        val requestToken = twitter.oAuthRequestToken
        if (sender is Player) Globals.requestTokens[sender.uniqueId] = requestToken
        else                  Globals.requestTokens[UUID(0, 0)] = requestToken

        sender.sendMessage("Please access this URL:")
        sender.sendMessage("${ChatColor.AQUA}${requestToken.authorizationURL}")
        sender.sendMessage("After authentication, please enter the PIN code displayed on your browser. /$baseCmd $PIN_CMD")
    }
}