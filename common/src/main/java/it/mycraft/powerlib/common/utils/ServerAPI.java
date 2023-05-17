package it.mycraft.powerlib.common.utils;

import lombok.Getter;

public class ServerAPI {

    @Getter
    private static ServerType type;

    static {
        loadType();
    }

    private static void loadType() {
        if (isUsingBukkit()) {
            type = ServerType.BUKKIT;
        }
        else if (isStrictlyUsingBungee()) {
            type = ServerType.BUNGEECORD;
        }
        else if (isUsingVelocity()) {
            type = ServerType.VELOCITY;
        }
        else type = ServerType.OTHER;
    }

    public static boolean isUsingBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static boolean isUsingBungee() { // might throw wrong server types e.g. when using Snap inside Velocity
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
