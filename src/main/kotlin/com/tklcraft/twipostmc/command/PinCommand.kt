package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.requestTokens
import com.tklcraft.twipostmc.twitterConfig
import com.tklcraft.twipostmc.twitterConfigFile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterException
import twitter4j.TwitterFactory

object PinCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = PIN_CMD,
        canRunPlayer = true,
        canRunServer = false,
        args = "<pin number>",
        description = "Used after entering /$TW_CMD $REGISTER_CMD"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        super.runCommand(sender, args)
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }

        if (sender !is Player) {
            sender.sendMessage("This command is for a client")
            return
        }

        try {
            val twitter = TwitterFactory.getSingleton()
            val accessToken = twitter.getOAuthAccessToken(
                    requestTokens[sender.uniqueId],
                    args.first()
            )
            twitterConfig.set("users.${sender.uniqueId}.mcName", sender.name)
            twitterConfig.set("users.${sender.uniqueId}.accessToken", accessToken.token)
            twitterConfig.set("users.${sender.uniqueId}.accessTokenSecret", accessToken.tokenSecret)
            twitterConfig.save(twitterConfigFile)

            requestTokens.remove(sender.uniqueId)

            sender.sendMessage("Twitter cooperation is complete!")
        } catch (te: TwitterException) {
            if (te.statusCode == 401) sender.sendMessage("Unable to get the access token.")
            else throw te
        }
    }
}