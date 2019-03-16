package com.tklcraft.twipostmc.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

object TWCommand : CommandExecutor, TabCompleter {
    private val subCmds : MutableMap<String, TWSubCommand> = TreeMap(String.CASE_INSENSITIVE_ORDER)

    init {
        addSubCommand(PostCommand)
    }

    private fun addSubCommand(command : TWSubCommand) {
        subCmds[command.name] = command
        command.aliases.forEach { subCmds[it] = command }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!command.name.equals(TW_CMD, ignoreCase = true)) return false

        if (args.isEmpty()) {
            return false
        }

        val subCmd = subCmds[args.first()] ?: return false

        if (subCmd.permission.isNotBlank() && !sender.hasPermission(subCmd.permission)) {
            sender.sendMessage("You don't have permission [${subCmd.permission}]")
            return true
        }

        subCmd.runCommand(sender, args.drop(1).toTypedArray())
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            val tabList = mutableListOf<String>()

            subCmds.forEach{
                if (sender.hasPermission(it.value.permission)) {
                    tabList.add(it.key)
                }
            }

            return tabList
        }

        return mutableListOf()
    }
}