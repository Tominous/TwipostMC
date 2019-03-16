package com.tklcraft.twipostmc.command

import org.bukkit.command.CommandSender
import java.lang.StringBuilder

abstract class TWSubCommand constructor(
        val baseCmd: String,
        val name: String,
        val aliases: Set<String> = setOf(),
        private val args: String = "", //e.g. "<param1> <param2> ... "
        val usage: String) {
    val permission: String
        get() = "$baseCmd.$name"

    open fun runCommand(sender: CommandSender, args: Array<out String>) {}

    fun sendUsage(sender: CommandSender) {
        val sb = StringBuilder().append("/$baseCmd $name ")
        if (args.isNotBlank()) sb.append("$args ")
        sb.append(usage)

        sender.sendMessage(sb.toString())
    }
}