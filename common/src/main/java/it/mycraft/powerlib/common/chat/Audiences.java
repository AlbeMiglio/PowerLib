package it.mycraft.powerlib.common.chat;

import it.mycraft.powerlib.common.utils.ServerAPI;
import lombok.Getter;

public class Audiences {

    @Getter
    private static PlatformAudience platformAudience = null;

    static {
        switch (ServerAPI.getType()) {
            case BUKKIT:
                platformAudience = new BukkitAudience();
                break;
            case BUNGEECORD:
                platformAudience = new BungeeAudience();
                break;
            case VELOCITY:
                platformAudience = new VelocityAudience();
                break;
            default:
            case OTHER:
                System.out.println("SEVERE ERROR! PowerLib is unable to find a server platform! Please contact an " +
                        "administrator ASAP!");
                break;
        }
    }
}
