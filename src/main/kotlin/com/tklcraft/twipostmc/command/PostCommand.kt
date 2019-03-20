package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.twitterConfig
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

object PostCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = POST_CMD,
        canRunPlayer = true,
        canRunServer = true,
        aliases = setOf(),
        args = """<message> | <"sample text">""",
        description = "Send a tweet"
) {
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        val sb = StringBuffer().append(parseArgs(args))
        if(sb.isEmpty()) {
            sendUsage(sender)
            return
        }

        if (sender !is Player) return

        val twitter = TwitterFactory.getSingleton()
        twitter.oAuthAccessToken = loadAccessToken(sender.uniqueId.toString())
        sb.append("\n#TwipostMC")
        val status = twitter.updateStatus(sb.toString())
        sender.sendMessage("Successfully tweet: ${status.text}")
    }

    private fun loadAccessToken(uuid: String) : AccessToken{
        val token = twitterConfig.getString("$uuid.accessToken")
        val tokenSecret = twitterConfig.getString("$uuid.accessTokenSecret")
        return AccessToken(token, tokenSecret)
    }

    private fun parseArgs(args: Array<out String>) : String {
        val str = buildString {
            args.forEach {
                append(it).append(" ")
            }
        }
        val matchResult = Regex(pattern = """".*"""").find(str, 0) ?: return ""
        return matchResult.value
    }
}