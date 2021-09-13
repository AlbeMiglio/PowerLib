package it.mycraft.powerlibexample.bukkit;

import it.mycraft.powerlib.reflection.ReflectionAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static it.mycraft.powerlib.reflection.ReflectionAPI.getNMSClass;

public class ExampleReflectionAPI {

    public void sendWelcomeTitle(Player p) {
        ReflectionAPI.sendTitle(p, "Welcome", "On our server", 20, 60, 20);
    }

    public void sendActionBar(Player p, String message) {
        try {
            Object icbc = getNMSClass("IChatBaseComponent").getClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
            Object packet = getNMSClass("PacketPlayOutChat").getConstructor(
                    getNMSClass("IChatBaseComponent"), byte.class).newInstance(icbc, (byte) 2);
            ReflectionAPI.sendPacket(p, packet);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException ignored) {
        }
    }
}
