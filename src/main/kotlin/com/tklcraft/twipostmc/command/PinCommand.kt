package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.twitterInstance
import org.bukkit.command.CommandSender
import twitter4j.TwitterException

object PinCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = PIN_CMD,
        args = "<pin number>",
        description = "Used after entering /$TW_CMD $REGISTER_CMD"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }

        try {

            val accessToken = twitterInstance.getOAuthAccessToken(
                    twitterInstance.oAuthRequestToken,
                    args.first()
            )
            sender.sendMessage(accessToken.token)
        } catch (te: TwitterException) {
            if (te.statusCode == 401) sender.sendMessage("Unable to get the access token.")
            else te.printStackTrace()
        }
    }
}