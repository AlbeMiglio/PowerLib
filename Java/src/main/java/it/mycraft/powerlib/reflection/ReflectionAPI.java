package it.mycraft.powerlib.reflection;

import it.mycraft.powerlib.bukkit.PowerLib;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

public class ReflectionAPI {

    /**
     * Returns The package-name of the NMS version
     */
    @Getter
    private static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * Returns The server's numerical version
     */
    @Getter
    private static int numericalVersion = Integer.parseInt(version.split("_")[1]);

    /**
     * @param name The path of a 'net.minecraft.server.v1_X_RX.' class
     * @return The NMS class object
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            PowerLib.getInstance().getLogger().log(Level.WARNING, "Error while finding a " + name + " NMS Class", ex);
            return null;
        }
    }

    /**
     * @param name The path of a 'org.bukkit.craftbukkit.v1_X_RX.' class
     * @return The OBC class object
     */
    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            PowerLib.getInstance().getLogger().log(Level.WARNING, "Error while finding a " + name + " OBC Class", ex);
            return null;
        }
    }

    /**
     * @param name The path of a 'org.bukkit.' class
     * @return The Bukkit class object
     */
    public static Class<?> getBukkitClass(String name) {
        try {
            return Class.forName("org.bukkit." + name);
        } catch (ClassNotFoundException ex) {
            PowerLib.getInstance().getLogger().log(Level.WARNING, "Error while finding a " + name + " OBC Class", ex);
            return null;
        }
    }

    /**
     * Sends a custom Title and Subtitle to a player
     *
     * @param player   The player to display the title to
     * @param title    The title being sent
     * @param subtitle The subtitle being sent
     * @param fadeIn   The "fade-in" time
     * @param stay     The "stay" time
     * @param fadeOut  The "fade-out" time
     */
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if(getNumericalVersion() > 16) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
        else try {
            Class<?> PacketPlayOutTitleClass = ReflectionAPI.getNMSClass("PacketPlayOutTitle");
            Class<?> IChatBaseComponentClass = ReflectionAPI.getNMSClass("IChatBaseComponent");

            Constructor<?> constructor = PacketPlayOutTitleClass.getConstructor(PacketPlayOutTitleClass
                    .getDeclaredClasses()[0], IChatBaseComponentClass, int.class, int.class, int.class);

            Object titleComponent = IChatBaseComponentClass.getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\":\"" + title + "\"}");
            Object subTitleComponent = IChatBaseComponentClass.getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\":\"" + subtitle + "\"}");

            Object titlePacket = constructor.newInstance(PacketPlayOutTitleClass
                    .getDeclaredClasses()[0].getField("TITLE").get(null), titleComponent, fadeIn, stay, fadeOut);
            Object subTitlePacket = constructor.newInstance(PacketPlayOutTitleClass
                    .getDeclaredClasses()[0].getField("SUBTITLE")
                    .get(null), subTitleComponent, fadeIn, stay, fadeOut);

            sendPacket(player, titlePacket);
            sendPacket(player, subTitlePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a packet to a certain player
     *
     * @param player The player to send the packet to
     * @param packet The packet being sent
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object playerHandle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player);
            Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{ReflectionAPI.getNMSClass("Packet")})
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
