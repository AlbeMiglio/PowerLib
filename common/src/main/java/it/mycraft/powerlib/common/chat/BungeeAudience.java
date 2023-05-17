package it.mycraft.powerlib.common.chat;

public class BungeeAudience extends PlatformAudience {

    protected BungeeAudience() {
        try {
            audienceAdapterClass = Class.forName("it.mycraft.powerlib.bungee.adapters.AudienceAdapter");
            commandSenderClass = Class.forName("net.md_5.bungee.api.CommandSender");
        }
        catch (ClassNotFoundException e) {
            sendError();
        }
    }
}
