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
    private val log = mutableMapOf<UUID, Date>()

    @EventHandler
    fun onPlayerLoginEvent(e : PlayerLoginEvent) {
        val player = e.player
        val uuid = player.uniqueId


        if (!pluginInstance.config.getBoolean("loginNotification")) {
            debug("Config: loginNotification is not defined.")
            return
        }

        val lastLogOut = log[uuid] ?: Date()

        val intervalTime = pluginInstance.config.getDouble("intervalTime") * 1000 * 60

        if ( Date().time - lastLogOut.time > intervalTime) {
            val message = pluginInstance.config.getString("loginTweetMessage") ?: run {
                debug("Config: loginTweetMessage is not define.")
                return
            }
            // val result = serverTweetPost(message.replace("%Player%".toRegex(), e.player.name))
            // debug("[Login notification]: $result")
            debug(message
                    .replace("%Player%".toRegex(), player.name)
                    .replace("%LastLogin%".toRegex(), sdf.format(lastLogOut)))

        }
    }
    @EventHandler
    fun onPlayerLogoutEvent (e : PlayerQuitEvent) {
        val player = e.player
        val uuid = player.uniqueId

        if (log[uuid] == null) {
            log[uuid] = Date()
            return
        }
    }
}