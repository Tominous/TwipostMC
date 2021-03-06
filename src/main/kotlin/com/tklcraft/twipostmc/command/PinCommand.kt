package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.Globals
import com.tklcraft.twipostmc.twitterConfig
import com.tklcraft.twipostmc.twitterConfigFile
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterException
import twitter4j.TwitterFactory
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
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }

        when (sender) {
            is Player -> saveTwitterAccountUser(sender, args.first())
            else -> saveTwitterAccountServer(sender.server, args.first())
        }
        sender.sendMessage("Twitter cooperation is complete!")
    }

    private fun saveTwitterAccountUser(player: Player, pin: String) {
        if (!Globals.requestTokens.containsKey(player.uniqueId)) {
            player.sendMessage("RequestToken does not exits. Enter /$TW_CMD register")
            return
        }
        try {
            val accessToken = twitter.getOAuthAccessToken(
                    Globals.requestTokens[player.uniqueId], pin
            )
            twitterConfig.set("users.${player.uniqueId}.mcName", player.name)
            twitterConfig.set("users.${player.uniqueId}.accessToken", accessToken.token)
            twitterConfig.set("users.${player.uniqueId}.accessTokenSecret", accessToken.tokenSecret)
            twitterConfig.save(twitterConfigFile)
            Globals.requestTokens.remove(player.uniqueId)
        } catch (te: TwitterException) {
            if (te.statusCode == 401) {
                player.sendMessage("Unable to get the access token.")
                player.sendMessage("Check the PIN code and enter it again or try again from register command.")
            }
            else player.sendMessage(te.errorMessage)
        } catch (e: Exception) {
            player.sendMessage(e.message!!)
            e.printStackTrace()
        }
    }

    private fun saveTwitterAccountServer(server: Server, pin: String) {
        if (!Globals.requestTokens.containsKey(UUID(0, 0))) {
            server.consoleSender.sendMessage("RequestToken does not exits. Enter /$TW_CMD register")
        }
        try {
            val uuid = UUID(0, 0)
            val accessToken = twitter.getOAuthAccessToken(
                    Globals.requestTokens[uuid], pin
            )
            twitterConfig.set("server.accessToken", accessToken.token)
            twitterConfig.set("server.accessTokenSecret", accessToken.tokenSecret)
            twitterConfig.save(twitterConfigFile)
            Globals.requestTokens.remove(uuid)
        } catch (te: TwitterException) {
            if (te.statusCode == 401) {
                server.consoleSender.sendMessage("Unable to the the access token.")
                server.consoleSender.sendMessage("Check the PIN code and enter it again or try again from register command.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}