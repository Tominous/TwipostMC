package com.tklcraft.twipostmc.command

import com.tklcraft.twipostmc.pluginInstance
import org.bukkit.command.CommandSender

object DelayTimeCommand : TWSubCommand(
        baseCmd = TW_CMD,
        name = DELAYTIME_CMD,
        canRunPlayer = true,
        canRunServer = true,
        args = "<delay time (seconds)>",
        description = "Set notification delay time")
{
    override fun runCommand(sender: CommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }

        val delayTime = args.first()
        if(!isNumber(delayTime)) {
            syntaxError(sender, "Delay time must be an integer.")
            return
        }

        val delayTimeInt = delayTime.toInt().let {
            if (it < 0) 0 else it
        }

        pluginInstance.config.set("delayTime", delayTimeInt)
        pluginInstance.saveConfig()

        sender.sendMessage("Set notification delay time: $delayTime")
    }

    private fun isNumber(num: String) : Boolean {
        return try {
            num.toInt()
            true
        } catch (e : NumberFormatException) {
            false
        }
    }
}