package com.tklcraft.twipostmc.listener

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import com.tklcraft.twipostmc.runnable.LoginNotificationTask
import com.tklcraft.twipostmc.runnable.LogoutNotificationTask
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.text.SimpleDateFormat
import java.util.*

object LogListener : Listener {
    private val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    private val delayTime = pluginInstance.config.getLong("delayTime" ) * 20

    @EventHandler
    fun onPlayerLoginEvent(e : PlayerLoginEvent) {
        val name = e.player.name
        val uuid = e.player.uniqueId

        if (!pluginInstance.config.getBoolean("notification")) {
            return
        }

        val messageTemplate = pluginInstance.config.getString("loginTweetMessage") ?: run {
            debug("Login tweet message is not defined.")
            return
        }

        val message = messageFormatter(messageTemplate, name)
        LoginNotificationTask(uuid, message).runTaskLater(pluginInstance, delayTime)
    }

    @EventHandler
    fun onPlayerQuitEvent (e : PlayerQuitEvent) {
        val name = e.player.name
        val uuid = e.player.uniqueId

        if (!pluginInstance.config.getBoolean("notification")) {
            return
        }

        val messageTemplate = pluginInstance.config.getString("logoutTweetMessage") ?: run {
            debug("Logout tweet message is not defined.")
            return
        }
        val message = messageFormatter(messageTemplate, name)
        LogoutNotificationTask(uuid, message).runTaskLater(pluginInstance, delayTime)

    }

    private fun messageFormatter(message: String, playerName : String) : String {
        return message
                .replace("%PLAYER%".toRegex(), playerName)
                .replace("%DATE%".toRegex(), sdf.format(Date()))
    }
}