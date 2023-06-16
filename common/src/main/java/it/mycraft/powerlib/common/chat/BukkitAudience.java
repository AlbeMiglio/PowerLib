package it.mycraft.powerlib.common.chat;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class BukkitAudience extends PlatformAudience {

    protected BukkitAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.bukkit.adapters.AudienceAdapter");
            commandSenderClass = Class.forName("org.bukkit.command.CommandSender");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }
}
