package it.mycraft.powerlib.common.chat;

import java.lang.reflect.Method;

public abstract class PlatformAudience {

    protected PlatformAudience() {
    }

    protected Class<?> audienceAdapterClass;
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

    protected abstract void loadPlayerAudience();

    protected abstract void loadConsoleAudience();

    protected abstract void loadAllPlayersAudience();

    protected abstract void loadAllAudience();

    protected abstract void loadPermissionAudience();

    protected abstract void loadFilterAudience();

    protected void sendError() {
        System.out.println("Exception encountered while loading PowerLib message instances. Please contact an administrator!");
    }
}
