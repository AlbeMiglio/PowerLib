package it.mycraft.powerlib.common.chat;

import it.mycraft.powerlib.common.utils.ServerAPI;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public abstract class PlatformAudience {

    protected PlatformAudience() {
    }

    protected Class<?> audienceAdapterClass;
    protected Class<?> commandSenderClass;
    protected Method playerAudience;
    protected Method consoleAudience;
    protected Method allPlayersAudience;
    protected Method allAudience;
    protected Method permissionAudience;
    protected Method filterAudience;

    protected Method getPlayerAudience() {
        if (playerAudience == null) {
            loadPlayerAudience();
        }
        return playerAudience;
    }

    protected Method getConsoleAudience() {
        if (consoleAudience == null) {
            loadConsoleAudience();
        }
        return consoleAudience;
    }

    protected Method getAllPlayersAudience() {
        if (allPlayersAudience == null) {
            loadAllPlayersAudience();
        }
        return allPlayersAudience;
    }

    protected Method getAllAudience() {
        if (allAudience == null) {
            loadAllAudience();
        }
        return allAudience;
    }

    protected Method getPermissionAudience() {
        if (permissionAudience == null) {
            loadPermissionAudience();
        }
        return permissionAudience;
    }

    protected Method getFilterAudience() {
        if (filterAudience == null) {
            loadFilterAudience();
        }
        return filterAudience;
    }

    protected void loadPlayerAudience() {
        try {
            playerAudience = audienceAdapterClass.getMethod("audience", commandSenderClass);
        } catch (Exception e) {
            sendError();
        }
    }

    protected void loadConsoleAudience() {
        try {
            consoleAudience = audienceAdapterClass.getMethod("console");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    protected void loadAllPlayersAudience() {
        try {
            allPlayersAudience = audienceAdapterClass.getMethod("players");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    protected void loadAllAudience() {
        try {
            allAudience = audienceAdapterClass.getMethod("all");
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    protected void loadPermissionAudience() {
        try {
            permissionAudience = audienceAdapterClass.getMethod("audience", String.class);
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    protected void loadFilterAudience() {
        try {
            filterAudience = audienceAdapterClass.getMethod("audience", Predicate.class);
        } catch (NoSuchMethodException e) {
            sendError();
        }
    }

    protected void sendError() {
        System.out.println("Exception encountered while loading PowerLib message instances. Please contact an administrator!");
    }
}
