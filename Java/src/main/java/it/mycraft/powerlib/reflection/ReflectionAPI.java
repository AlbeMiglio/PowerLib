package it.mycraft.powerlib.reflection;

import it.mycraft.powerlib.PowerLib;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ReflectionAPI {

    /**
     * @return the package-name of the NMS version
     */
    @Getter
    private static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * @return the server's numerical version
     */
    @Getter
    private static int numericalVersion = Integer.parseInt(version.split("_")[2]);

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

}
