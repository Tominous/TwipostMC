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
        val twitter = TwitterFactory.getSingleton()
        twitter.setOAuthConsumer(
                pluginInstance.config.getString("consumerKey"),
                pluginInstance.config.getString("consumerSecret")
        )
    }
}
