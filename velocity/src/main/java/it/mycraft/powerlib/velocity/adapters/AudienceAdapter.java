package it.mycraft.powerlib.velocity.adapters;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.velocity.PowerLib;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.audience.Audience;

import java.util.function.Predicate;

public class AudienceAdapter {

    private AudienceAdapter() {

    }

    private static final PowerLib powerLib = PowerLib.getInstance();

    public static Audience audience(CommandSource sender) {
        return sender;
    }

    public static Audience audience(Player player) {
        return player;
    }

    public static Audience audience(String permission) {
        return powerLib.getProxy().filterAudience((audience -> ((CommandSource) audience).hasPermission(permission)));
    }

    public static Audience audience(Predicate<Audience> predicate) {
        return powerLib.getProxy().filterAudience(predicate);
    }

    public static Audience all() {
        return powerLib.getProxy().filterAudience(audience -> true);
    }

    public static Audience players() {
        return powerLib.getProxy().filterAudience(audience -> audience instanceof Player);
    }

    public static Audience server(String serverName) {
        return powerLib.getProxy().filterAudience(a -> a instanceof Player
                && ((Player) a).getCurrentServer().isPresent()
                && ((Player) a).getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(serverName));
    }

    public static Audience console() {
        return powerLib.getProxy().getConsoleCommandSource();
    }
}
