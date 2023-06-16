package it.mycraft.powerlib.velocity.adapters;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.velocity.PowerLib;
import net.kyori.adventure.audience.Audience;

import java.util.function.Predicate;

public class AudienceAdapter {

    private AudienceAdapter() {

    }

    public static Audience audience(CommandSource sender) {
        return sender;
    }

    public static Audience audience(Player player) {
        return player;
    }

    public static Audience audience(String permission) {
        return PowerLib.getProxy().filterAudience((audience -> ((CommandSource) audience).hasPermission(permission)));
    }

    public static Audience audience(Predicate<Audience> predicate) {
        return PowerLib.getProxy().filterAudience(predicate);
    }

    public static Audience all() {
        return PowerLib.getProxy().filterAudience(audience -> true);
    }

    public static Audience players() {
        return PowerLib.getProxy().filterAudience(audience -> audience instanceof Player);
    }

    public static Audience server(String serverName) {
        return PowerLib.getProxy().filterAudience(a -> a instanceof Player
                && ((Player) a).getCurrentServer().isPresent()
                && ((Player) a).getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(serverName));
    }

    public static Audience console() {
        return PowerLib.getProxy().getConsoleCommandSource();
    }
}
