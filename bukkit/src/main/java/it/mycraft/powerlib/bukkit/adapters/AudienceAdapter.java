package it.mycraft.powerlib.bukkit.adapters;

import it.mycraft.powerlib.bukkit.PowerLib;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class AudienceAdapter {

    private AudienceAdapter() {

    }

    private static final PowerLib powerLib = PowerLib.getInstance();

    public static Audience audience(CommandSender sender) {
        return powerLib.adventure().sender(sender);
    }

    public static Audience audience(Player player) {
        return powerLib.adventure().player(player);
    }

    public static Audience audience(String permission) {
        return powerLib.adventure().permission(permission);
    }

    public static Audience audience(Predicate<CommandSender> predicate) { return powerLib.adventure().filter(predicate); }

    public static Audience audience(World world) {
        return powerLib.adventure().world(Key.key(world.getName()));
    }

    public static Audience all() {
        return powerLib.adventure().all();
    }

    public static Audience players() {
        return powerLib.adventure().players();
    }

    public static Audience console() {
        return powerLib.adventure().console();
    }
}
