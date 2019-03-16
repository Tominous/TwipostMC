package com.tklcraft.twipostmc.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.lang.StringBuilder

abstract class TWSubCommand constructor(
        val baseCmd: String,
        val name: String,
        val aliases: Set<String> = setOf(),
        private val args: String = "", //e.g. "<param1> <param2> ..."
        val description: String) {
    val usage: String
        get() = "/$baseCmd $name $args"
    val permission: String
        get() = "$baseCmd.$name"

    open fun runCommand(sender: CommandSender, args: Array<out String>) {}

    fun sendUsage(sender: CommandSender) {
        sender.sendMessage(usage)
    }

    fun sendDescription(sender: CommandSender) {
        sender.sendMessage(description)
    }

    fun syntaxError(sender: CommandSender, message: String) {
        if (message.isBlank()) return

        sender.sendMessage("${ChatColor.YELLOW}$message")
    }
}