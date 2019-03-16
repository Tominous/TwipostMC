package com.tklcraft.twipostmc

import com.tklcraft.twipostmc.command.TWCommand
import com.tklcraft.twipostmc.command.TW_CMD
import org.bukkit.plugin.java.JavaPlugin

class TwipostMCPlugin : JavaPlugin() {
    override fun onEnable() {
        registerCommand()
        debug("TwipostMC command register")

        saveDefaultConfig()
        debug("Save default config")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerCommand() {
        getCommand(TW_CMD)?.setExecutor(TWCommand)
    }
}
