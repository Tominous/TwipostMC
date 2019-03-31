package com.tklcraft.twipostmc.listener

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import com.tklcraft.twipostmc.serverTweetPost
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.text.SimpleDateFormat
import java.util.*

object LogListener : Listener {
    private val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    private val initDate = Date(0)
    private val logins = mutableMapOf<UUID, Date>()
    private val logouts = mutableMapOf<UUID, Date>()
    private val intervalTime = pluginInstance.config.getDouble("intervalTime") * 1000 * 60

    @EventHandler
    fun onPlayerLoginEvent(e : PlayerLoginEvent) {
        val currentDate = Date()
        val player = e.player
        val uuid = player.uniqueId
        val lastLogin = logins[uuid] ?: initDate
        logins[uuid] = currentDate

        if (!pluginInstance.config.getBoolean("notification")) {
            return
        }

        if (currentDate.time - lastLogin.time > intervalTime) {
            val messageTemplate = pluginInstance.config.getString("loginTweetMessage") ?: run {
                debug("Login tweet message is not defined.")
                return
            }
            val message = messageFormatter(messageTemplate, player)
            val result = serverTweetPost(message)
            debug(result)
        }
    }

    @EventHandler
    fun onPlayerQuitEvent (e : PlayerQuitEvent) {
        val currentDate = Date()
        val player = e.player
        val uuid = player.uniqueId
        val lastLogin = logins[uuid] ?: initDate
        val lastLogout = logouts[uuid] ?: initDate
        logouts[uuid] = currentDate

        if (!pluginInstance.config.getBoolean("notification")) {
            return
        }

        if (currentDate.time - lastLogin.time > intervalTime &&
                currentDate.time - lastLogout.time > intervalTime) {
            val messageTemplate = pluginInstance.config.getString("logoutTweetMessage") ?: run {
                debug("Logout tweet message is not defined.")
                return
            }
            val message = messageFormatter(messageTemplate, player)
            val result = serverTweetPost(message)
            debug(result)
        }
    }

    private fun messageFormatter(message: String, player: Player) : String {
        return message
                .replace("%Player%".toRegex(), player.name)
    }
}