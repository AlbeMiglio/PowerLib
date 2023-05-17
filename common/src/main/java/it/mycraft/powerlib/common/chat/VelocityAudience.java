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
}
