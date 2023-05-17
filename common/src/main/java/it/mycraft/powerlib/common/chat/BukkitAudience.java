package it.mycraft.powerlib.common.chat;

import java.util.function.Predicate;

public class BukkitAudience extends PlatformAudience {

    protected BukkitAudience() {
        audienceAdapterClassName = "it.mycraft.powerlib.bukkit.adapters.AudienceAdapter";
    }

    @Override
    protected void loadPlayerAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            Class<?> commandSenderClass = Class.forName("org.bukkit.command.CommandSender");
            playerAudience = audienceAdapterClass.getMethod("audience", commandSenderClass);
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadConsoleAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            playerAudience = audienceAdapterClass.getMethod("console");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllPlayersAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            playerAudience = audienceAdapterClass.getMethod("players");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            playerAudience = audienceAdapterClass.getMethod("all");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadPermissionAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            playerAudience = audienceAdapterClass.getMethod("audience", String.class);
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadFilterAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName(audienceAdapterClassName);
            playerAudience = audienceAdapterClass.getMethod("audience", Predicate.class);
        } catch (Exception e) {
            sendError();
        }
    }
}
