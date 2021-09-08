package it.mycraft.powerlib.common.utils;

public class ServerAPI {

    public static boolean isUsingBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static boolean isUsingBungee() {
        try {
            Class.forName("net.md_5.bungee.api.ProxyServer");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static boolean isStrictlyUsingBungee() {
        try {
            Class.forName("net.md_5.bungee.BungeeCord");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static boolean isUsingVelocity() {
        try {
            Class.forName("com.velocitypowered.api.proxy.ProxyServer");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static boolean isStrictlyUsingVelocity() {
        try {
            Class.forName("com.velocitypowered.proxy.Velocity");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }
}
