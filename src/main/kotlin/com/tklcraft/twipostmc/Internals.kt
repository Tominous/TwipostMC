package com.tklcraft.twipostmc

import org.bukkit.Bukkit.getPluginManager
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

internal val pluginInstance : TwipostMCPlugin by lazy {
    val instance = getPluginManager().getPlugin("TwipostMC")
    requireNotNull(instance) {"pluginInstance must not be null"}
    return@lazy instance as TwipostMCPlugin
}

internal val twitterInstance : Twitter = TwitterFactory(
        ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(pluginInstance.config.getString("consumerKey"))
                .setOAuthConsumerSecret(pluginInstance.config.getString("consumerSecret"))
                .build())
        .instance


internal fun info(message: String) = pluginInstance.logger.info(message)
internal fun warning(message: String) = pluginInstance.logger.warning(message)
internal fun debug(message: String) {
    if (pluginInstance.config.getBoolean("debug")) {
        info("[DEBUG] $message")
    }
}
