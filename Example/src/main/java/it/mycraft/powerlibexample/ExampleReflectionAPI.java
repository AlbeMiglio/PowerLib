package it.mycraft.powerlibexample;

import it.mycraft.powerlib.reflection.ReflectionAPI;
import org.bukkit.entity.Player;

public class ExampleReflectionAPI {

    public void sendWelcomeTitle(Player p) {
        ReflectionAPI.sendTitle(p, "Welcome", "On our server", 20, 60, 20);
    }
}
