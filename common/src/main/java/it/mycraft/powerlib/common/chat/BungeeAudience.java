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

    @Override
    protected void loadConsoleAudience() {
        try {
            consoleAudience = audienceAdapterClass.getMethod("console");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    @Override
    protected void loadAllPlayersAudience() {
        try {
            allPlayersAudience = audienceAdapterClass.getMethod("players");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    @Override
    protected void loadAllAudience() {
        try {
            allAudience = audienceAdapterClass.getMethod("all");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    @Override
    protected void loadPermissionAudience() {
        try {
            permissionAudience = audienceAdapterClass.getMethod("audience", String.class);
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    @Override
    protected void loadFilterAudience() {
        try {
            filterAudience = audienceAdapterClass.getMethod("audience", Predicate.class);
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }
}
