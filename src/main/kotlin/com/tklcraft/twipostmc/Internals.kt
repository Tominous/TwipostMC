package com.tklcraft.twipostmc

import org.bukkit.Bukkit.getPluginManager
import org.bukkit.configuration.file.YamlConfiguration
import twitter4j.auth.RequestToken
import java.io.File
import java.util.*

internal val pluginInstance : TwipostMCPlugin by lazy {
    val instance = getPluginManager().getPlugin("TwipostMC")
    requireNotNull(instance) {"pluginInstance must not be null"}
    return@lazy instance as TwipostMCPlugin
}

internal val twitterConfigFile = File(pluginInstance.dataFolder, "usertoken.yml")
internal val  twitterConfig : YamlConfiguration by lazy {
    return@lazy YamlConfiguration.loadConfiguration(twitterConfigFile)
}
internal val requestTokens = mutableMapOf<UUID, RequestToken>()

internal fun info(message: String) = pluginInstance.logger.info(message)
internal fun warning(message: String) = pluginInstance.logger.warning(message)
internal fun debug(message: String) {
    if (pluginInstance.config.getBoolean("debug")) {
        info("[DEBUG] $message")
    }
}
