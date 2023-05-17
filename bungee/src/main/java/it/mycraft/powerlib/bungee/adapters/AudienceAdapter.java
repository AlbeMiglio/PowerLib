package it.mycraft.powerlib.bungee.adapters;

import it.mycraft.powerlib.bungee.PowerLib;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Predicate;

public class AudienceAdapter {

    private AudienceAdapter() {

    }

    private static final PowerLib powerLib = PowerLib.getInstance();

    public static Audience audience(CommandSender sender) {
        return powerLib.adventure().sender(sender);
    }

    public static Audience audience(ProxiedPlayer player) {
        return powerLib.adventure().player(player);
    }

    public static Audience audience(String permission) {
        return powerLib.adventure().permission(permission);
    }

    public static Audience audience(Predicate<CommandSender> predicate) { return powerLib.adventure().filter(predicate); }

    public static Audience all() {
        return powerLib.adventure().all();
    }

    public static Audience players() {
        return powerLib.adventure().players();
    }

    public static Audience server(String serverName) {
        return powerLib.adventure().server(serverName);
    }

    public static Audience console() {
        return powerLib.adventure().console();
    }
}
