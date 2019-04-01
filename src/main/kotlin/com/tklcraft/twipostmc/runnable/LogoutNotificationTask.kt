package com.tklcraft.twipostmc.runnable

import com.tklcraft.twipostmc.Globals
import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.pluginInstance
import com.tklcraft.twipostmc.serverTweetPost
import org.bukkit.Bukkit.getPlayer
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class LogoutNotificationTask(private val uuid: UUID,
                             private val message: String
) : BukkitRunnable() {
    @Override
    override fun run() {
        if (getPlayer(uuid) != null) return

        val loginNotificationFlag = Globals.loginNotificationFlags[uuid] ?: false
        if (loginNotificationFlag) return

        serverTweetPost(message)

        Globals.loginNotificationFlags[uuid] = true
        Globals.logoutNotificationFlags[uuid] = false
    }
}