package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import org.bukkit.command.CommandSender

object DebugCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = DEBUG_CMD,
        canRunPlayer = false,
        canRunServer = true,
        description = "Change debug mode on/off")
{
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        val debugMode = !pluginInstance.config.getBoolean("debug")
        pluginInstance.config.set("debug", debugMode)
        pluginInstance.saveConfig()

        if (debugMode) sender.sendMessage("Debug mode on.")
        else sender.sendMessage("Debug mode off.")
    }
}