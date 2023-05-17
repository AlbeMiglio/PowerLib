package it.mycraft.powerlib.common.chat;

public class VelocityAudience extends PlatformAudience {
    
    protected VelocityAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.velocity.adapters.AudienceAdapter");
            commandSenderClass = Class.forName("com.velocitypowered.api.command.CommandSource");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }
}
