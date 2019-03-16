package com.tklcraft.twipostmc

import org.bukkit.Bukkit.getPluginManager

internal val pluginInstance : TwipostMCPlugin by lazy {
    val instance = getPluginManager().getPlugin("TwipostMC")
    requireNotNull(instance) {"pluginInstance must not be null"}
    return@lazy instance as TwipostMCPlugin
}

internal fun info(message: String) = pluginInstance.logger.info(message)
internal fun warning(message: String) = pluginInstance.logger.warning(message)
internal fun debug(message: String) {
    if (pluginInstance.config.getBoolean("debug")) {
        info("[DEBUG] $message")
    }
}