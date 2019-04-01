package com.tklcraft.twipostmc

import org.bukkit.Bukkit.getPluginManager
import org.bukkit.configuration.file.YamlConfiguration
import twitter4j.StatusUpdate
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import java.io.File
import java.util.*

internal val pluginInstance : TwipostMCPlugin by lazy {
    val instance = getPluginManager().getPlugin("TwipostMC")
    requireNotNull(instance) {"pluginInstance must not be null"}
    return@lazy instance as TwipostMCPlugin
}

internal val twitterConfigFile : File by lazy {
    val dir = pluginInstance.dataFolder
    val fileName = "tokens.yml"
    if (!File(dir, fileName).exists()) {
        pluginInstance.saveResource(fileName, false)
    }
    return@lazy File(dir, fileName)
}

internal val  twitterConfig : YamlConfiguration by lazy {
    return@lazy YamlConfiguration.loadConfiguration(twitterConfigFile)
}

internal object Globals {
    val requestTokens = mutableMapOf<UUID, RequestToken>()
    val loginNotificationFlags = mutableMapOf<UUID, Boolean>()
    val logoutNotificationFlags = mutableMapOf<UUID, Boolean>()
}

internal fun serverTweetPost(message: String) : String {
    val acsToken = twitterConfig.getString("server.accessToken")
            ?: return "server.accessToken is not found"
    val acsTokenSecret = twitterConfig.getString("server.accessTokenSecret")
            ?: return "server.accessTokenSecret is not found"
    val accessToken = AccessToken(acsToken, acsTokenSecret)
    return tweetPost(accessToken, message)
}

internal fun tweetPost(accessToken: AccessToken, message: String) : String {
    val hashTags = pluginInstance.config.getStringList("hashTag")
    val hashTagMessage = buildString {
        append(message)
        hashTags.forEach { hashTag ->
            when (hashTag) {
                hashTags.first() -> append("\n#$hashTag")
                else             -> append(" #$hashTag")
            }
        }
    }

    val twitter = TwitterFactory.getSingleton()
    twitter.oAuthAccessToken = accessToken
    val statusUpdate = StatusUpdate(hashTagMessage)
    val status = twitter.updateStatus(statusUpdate)

    val consoleMessage = "Tweet message sending: " + status.text.replace("\n", " ")
    pluginInstance.server.consoleSender.sendMessage(consoleMessage)
    return status.text
}
internal fun info(message: String) = pluginInstance.logger.info(message)
internal fun warning(message: String) = pluginInstance.logger.warning(message)
internal fun debug(message: String) {
    if (pluginInstance.config.getBoolean("debug")) {
        info("[DEBUG] $message")
    }
}
