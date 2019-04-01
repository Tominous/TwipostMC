package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import org.bukkit.command.CommandSender

object NotificationCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = NOTIFICATION_CMD,
        canRunPlayer = false,
        canRunServer = true,
        description = "Change login/logout notification on/off")
{
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        val isNotification = !pluginInstance.config.getBoolean("notification")
        pluginInstance.config.set("notification", isNotification)
        pluginInstance.saveConfig()

        if (isNotification) sender.sendMessage("Notification on.")
        else sender.sendMessage("Notification off.")
    }
}