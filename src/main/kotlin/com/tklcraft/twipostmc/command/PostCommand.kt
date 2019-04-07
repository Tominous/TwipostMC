package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.tweetPost
import com.tklcraft.twipostmc.twitterConfig
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.auth.AccessToken

object PostCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = POST_CMD,
        canRunPlayer = true,
        canRunServer = false,
        aliases = setOf(),
        args = """<message> | <"sample text">""",
        description = "Send a tweet"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        val sb = StringBuffer().append(connectArgs(args))
        if(sb.isEmpty()) {
            sendUsage(sender)
            return
        }


        if (sender !is Player) return
        tweetPost(loadAccessToken(sender.uniqueId.toString()), sb.toString())
        sender.sendMessage("Successful tweet post")
    }

    private fun loadAccessToken(uuid: String) : AccessToken{
        val token = twitterConfig.getString("users.$uuid.accessToken")
        val tokenSecret = twitterConfig.getString("users.$uuid.accessTokenSecret")
        return AccessToken(token, tokenSecret)
    }

    private fun connectArgs(args: Array<out String>) : String {
        if (args.isEmpty()) return ""
        val str = buildString {
            args.forEach {
                append(it).append(" ")
            }
        }
        val matchResult = Regex(pattern = """"(.+?)"""").find(str, 0) ?: return args.first()
        return matchResult.value.substring(1, matchResult.value.length -1)
    }

}