package it.mycraft.powerlib.common.chat;

import java.util.function.Predicate;

public class BungeeAudience extends PlatformAudience {

    protected BungeeAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.bungee.adapters.AudienceAdapter");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }

    @Override
    protected void loadPlayerAudience() {
        try {
            Class<?> commandSenderClass = Class.forName("net.md_5.bungee.api.CommandSender");
            playerAudience = audienceAdapterClass.getMethod("audience", commandSenderClass);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            sendError();
        }
    }
}
