package it.mycraft.powerlib.bukkit.adapters;

import it.mycraft.powerlib.bukkit.PowerLib;
import lombok.NonNull;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class AudienceAdapter {

    private AudienceAdapter() {

    }

    public static Audience audience(CommandSender sender) {
        return PowerLib.adventure().sender(sender);
    }

    public static Audience audience(Player player) {
        return PowerLib.adventure().player(player);
    }

    public static Audience audience(String permission) {
        return PowerLib.adventure().permission(permission);
    }

    public static Audience audience(Predicate<CommandSender> predicate) { return PowerLib.adventure().filter(predicate); }

    public static Audience audience(World world) {
        return PowerLib.adventure().world(Key.key(world.getName()));
    }

    public static Audience all() {
        return PowerLib.adventure().all();
    }

    public static Audience players() {
        return PowerLib.adventure().players();
    }

    public static Audience console() {
        return PowerLib.adventure().console();
    }
}
