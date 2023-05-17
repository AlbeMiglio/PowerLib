package it.mycraft.powerlib.common.chat;

import java.util.function.Predicate;

public class VelocityAudience extends PlatformAudience {
    
    protected VelocityAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }

    @Override
    protected void loadPlayerAudience() {
        try {
            Class<?> commandSenderClass = Class.forName("com.velocitypowered.api.command.CommandSource");
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
