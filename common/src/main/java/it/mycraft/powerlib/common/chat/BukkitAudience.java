package it.mycraft.powerlib.common.chat;

import java.util.function.Predicate;

public class BukkitAudience extends PlatformAudience {

    protected BukkitAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.bukkit.adapters.AudienceAdapter");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }

    @Override
    protected void loadPlayerAudience() {
        try {
            Class<?> commandSenderClass = Class.forName("org.bukkit.command.CommandSender");
            playerAudience = audienceAdapterClass.getMethod("audience", commandSenderClass);
        } catch (Exception e) {
            sendError();
        }
    }
}
