package com.tklcraft.twipostmc.runnable

import com.tklcraft.twipostmc.Globals
import com.tklcraft.twipostmc.debug
import com.tklcraft.twipostmc.serverTweetPost
import org.bukkit.Bukkit.getPlayer
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class LoginNotificationTask(private val uuid: UUID,
                            private val message: String
) : BukkitRunnable() {
    @Override
    override fun run() {
        if (getPlayer(uuid) == null) return

        val logoutNotificationFlag = Globals.logoutNotificationFlags[uuid] ?: false
        if (logoutNotificationFlag) return

        serverTweetPost(message)

        Globals.loginNotificationFlags[uuid] = false
        Globals.logoutNotificationFlags[uuid] = true
    }
}