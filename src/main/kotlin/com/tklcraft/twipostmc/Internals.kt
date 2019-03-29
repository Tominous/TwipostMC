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
internal val requestTokens = mutableMapOf<UUID, RequestToken>()

internal fun serverTweetPost(message: String) : String {
    val acsToken = pluginInstance.config.getString("accessToken")
            ?: return "server.accessToken is not found"
    val acsTokenSecret = pluginInstance.config.getString("accessTokenSecret")
            ?: return "server.accessTokenSecret is not found"
    val accessToken = AccessToken(acsToken, acsTokenSecret)
    return tweetPost(accessToken, message)
}

internal fun tweetPost(accessToken: AccessToken, message: String) : String {
    val twitter = TwitterFactory.getSingleton()
    twitter.oAuthAccessToken = accessToken
    val statusUpdate = StatusUpdate(message)
    val status = twitter.updateStatus(statusUpdate)
    return status.text
}
internal fun info(message: String) = pluginInstance.logger.info(message)
internal fun warning(message: String) = pluginInstance.logger.warning(message)
internal fun debug(message: String) {
    if (pluginInstance.config.getBoolean("debug")) {
        info("[DEBUG] $message")
    }
}
