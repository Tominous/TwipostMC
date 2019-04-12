package com.tklcraft.twipostmc

import com.tklcraft.twipostmc.command.TWCommand
import com.tklcraft.twipostmc.command.TW_CMD
import com.tklcraft.twipostmc.listener.LogListener
import org.bukkit.plugin.java.JavaPlugin
import twitter4j.TwitterFactory

class TwipostMCPlugin : JavaPlugin() {
    override fun onEnable() {
        registerCommand()
        debug("TwipostMC commands register")

        registerEvents()
        debug("TwipostMC events register")

        saveDefaultConfig()
        debug("Save default config")

        setTwitterOAuth()
        debug("Set Twitter OAuth")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerCommand() {
        getCommand(TW_CMD)?.setExecutor(TWCommand)
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(LogListener, this)
    }

    private fun setTwitterOAuth() {
        // TODO: Change the following Twitter API key
        val defaultConsumerKey = "changeme"
        val defaultConsumerSecret = "changeme"

        val configConsumerKey = twitterConfig.getString("server.consumerKey") ?: ""
        val configConsumerSecret = twitterConfig.getString("server.consumerSecret") ?: ""

        val consumerKey = if (configConsumerKey.isNotBlank()) configConsumerKey else defaultConsumerKey
        val consumerSecret = if (configConsumerSecret.isNotBlank()) configConsumerSecret else defaultConsumerSecret

        TwitterFactory.getSingleton().setOAuthConsumer(consumerKey, consumerSecret)
    }
}
