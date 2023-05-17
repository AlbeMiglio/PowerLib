package it.mycraft.powerlib.common.chat;

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
