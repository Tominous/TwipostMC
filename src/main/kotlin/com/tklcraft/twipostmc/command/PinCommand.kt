package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.requestTokens
import com.tklcraft.twipostmc.twitterConfig
import com.tklcraft.twipostmc.twitterConfigFile
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import java.util.*

object PinCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = PIN_CMD,
        canRunPlayer = true,
        canRunServer = true,
        args = "<pin number>",
        description = "Used after entering /$TW_CMD $REGISTER_CMD"
) {
    private val twitter = TwitterFactory.getSingleton()
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        super.runCommand(sender, args)
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }

        when (sender) {
            is Player -> connectTwitterAccountUser(sender, args.first())
            else -> connectTwitterAccountServer(sender as Server, args.first())
        }
        sender.sendMessage("Twitter cooperation is complete!")
    }

    private fun connectTwitterAccountUser(player: Player, pin: String) {
        try {
            val accessToken = twitter.getOAuthAccessToken(
                    requestTokens[player.uniqueId], pin
            )
            twitterConfig.set("users.${player.uniqueId}.mcName", player.name)
            twitterConfig.set("users.${player.uniqueId}.accessToken", accessToken.token)
            twitterConfig.set("users.${player.uniqueId}.accessTokenSecret", accessToken.tokenSecret)
            twitterConfig.save(twitterConfigFile)
            requestTokens.remove(player.uniqueId)
        } catch (te: TwitterException) {
            if (te.statusCode == 401) player.sendMessage("Unable to get the access token.")
            else throw te
        }
    }

    private fun connectTwitterAccountServer(server: Server, pin: String) {
        try {
            val uuid = UUID(0, 0)
            val accessToken = twitter.getOAuthAccessToken(
                    requestTokens[uuid], pin
            )
            twitterConfig.set("server.accessToken", accessToken.token)
            twitterConfig.set("server.accessTokenSecret", accessToken.tokenSecret)
            twitterConfig.save(twitterConfigFile)
            requestTokens.remove(uuid)
        } catch (te: TwitterException) {
            if (te.statusCode == 401) server.consoleSender.sendMessage("Unable to the the access token.")
        }
    }

}