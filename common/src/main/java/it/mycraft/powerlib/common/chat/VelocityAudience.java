package it.mycraft.powerlib.common.chat;

import it.mycraft.powerlib.common.chat.PlatformAudience;

import java.util.function.Predicate;

public class VelocityAudience extends PlatformAudience {

    @Override
    protected void loadPlayerAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            Class<?> commandSenderClass = Class.forName("com.velocitypowered.api.command.CommandSource");
            playerAudience = audienceAdapterClass.getMethod("audience", commandSenderClass);
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadConsoleAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            playerAudience = audienceAdapterClass.getMethod("console");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllPlayersAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            playerAudience = audienceAdapterClass.getMethod("players");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadAllAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            playerAudience = audienceAdapterClass.getMethod("all");
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadPermissionAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            playerAudience = audienceAdapterClass.getMethod("audience", String.class);
        } catch (Exception e) {
            sendError();
        }
    }

    @Override
    protected void loadFilterAudience() {
        try {
            Class<?> audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            playerAudience = audienceAdapterClass.getMethod("audience", Predicate.class);
        } catch (Exception e) {
            sendError();
        }
    }
}
