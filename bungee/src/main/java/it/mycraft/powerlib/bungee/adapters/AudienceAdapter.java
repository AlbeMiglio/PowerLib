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

    public static Audience audience(CommandSender sender) {
        return PowerLib.adventure().sender(sender);
    }

    public static Audience audience(ProxiedPlayer player) {
        return PowerLib.adventure().player(player);
    }

    public static Audience audience(String permission) {
        return PowerLib.adventure().permission(permission);
    }

    public static Audience audience(Predicate<CommandSender> predicate) { return PowerLib.adventure().filter(predicate); }

    public static Audience all() {
        return PowerLib.adventure().all();
    }

    public static Audience players() {
        return PowerLib.adventure().players();
    }

    public static Audience server(String serverName) {
        return PowerLib.adventure().server(serverName);
    }

    public static Audience console() {
        return PowerLib.adventure().console();
    }
}
