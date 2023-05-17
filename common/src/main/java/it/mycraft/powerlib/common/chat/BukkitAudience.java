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

    @Override
    protected void loadConsoleAudience() {
        try {
            consoleAudience = audienceAdapterClass.getMethod("console");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllPlayersAudience() {
        try {
            allPlayersAudience = audienceAdapterClass.getMethod("players");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllAudience() {
        try {
            allAudience = audienceAdapterClass.getMethod("all");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadPermissionAudience() {
        try {
            permissionAudience = audienceAdapterClass.getMethod("audience", String.class);
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadFilterAudience() {
        try {
            filterAudience = audienceAdapterClass.getMethod("audience", Predicate.class);
        } catch (Exception e) {
            sendError();
        }
    }
}
