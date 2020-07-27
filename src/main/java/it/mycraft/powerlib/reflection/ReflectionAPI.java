package it.mycraft.powerlib.reflection;

import lombok.Getter;
import org.bukkit.Bukkit;

public class ReflectionAPI {

    @Getter
    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
