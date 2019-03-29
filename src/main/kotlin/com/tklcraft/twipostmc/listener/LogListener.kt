package com.tklcraft.twipostmc.listener

import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.text.SimpleDateFormat
import java.util.*

object LogListener : Listener {
    private val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    private val login = mutableMapOf<UUID, Date>()
    private val logout = mutableMapOf<UUID, Date>()
    private val intervalTime = pluginInstance.config.getDouble("intervalTime") * 1000 * 60

    @EventHandler
    fun onPlayerLoginEvent(e : PlayerLoginEvent) {
        val player = e.player
        val uuid = player.uniqueId
        val lastLogin = login[uuid] ?: run {
            login[uuid] = Date()
            debug("${player.name} is first login.")
            return
        }
        val lastLogout = logout[uuid] ?: Date()

        if (Date().time - lastLogout.time < intervalTime) {
            login[uuid] = Date()
        }

        if (!pluginInstance.config.getBoolean("loginNotification")) {
            return
        }
        if ( Date().time - lastLogin.time > intervalTime) {
            val message = pluginInstance.config.getString("loginTweetMessage") ?: run {
                debug("Config: loginTweetMessage is not define.")
                return
            }
            // val result = serverTweetPost(message.replace("%Player%".toRegex(), e.player.name))
            // debug("[Login notification]: $result")
            debug(message
                    .replace("%Player%".toRegex(), player.name)
                    .replace("%LastLogout%".toRegex(), sdf.format(lastLogin)))

        }
    }
    @EventHandler
    fun onPlayerLogoutEvent (e : PlayerQuitEvent) {
        val player = e.player
        val uuid = player.uniqueId
        val lastLogin = login[uuid] ?: Date()
        val lastLogout = logout[uuid] ?: Date()

        if (Date().time - lastLogin.time < intervalTime) {
            logout[uuid] = Date()
        }

        if (!pluginInstance.config.getBoolean("loginNotification")) {
            return
        }
    }
}