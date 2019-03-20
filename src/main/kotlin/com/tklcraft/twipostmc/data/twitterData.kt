package com.tklcraft.twipostmc.data

import com.tklcraft.twipostmc.pluginInstance
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class TwitterData(val uuid : String) {
    val config = YamlConfiguration.loadConfiguration(File(pluginInstance.dataFolder, "twitter.yml"))

}