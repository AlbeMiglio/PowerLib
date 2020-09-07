package it.mycraft.powerlib;

import it.mycraft.powerlib.chat.Message;
import it.mycraft.powerlib.updater.PluginUpdater;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PowerLib Official Source code
 *
 * @authors AlbeMiglio, pompiere1
 * https://www.github.com/AlbeMiglio/PowerLib
 */
public class PowerLib extends JavaPlugin implements Listener {

    @Getter
    private static PowerLib instance;

    private PluginUpdater pluginUpdater;

    public void onEnable() {
        instance = this;
        this.pluginUpdater = new PluginUpdater(this).setSpigotURL(83274);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp() && !player.hasPermission("powerlib.update")) {
            return;
        }
        if (this.pluginUpdater.needsUpdate()) {
            Message m = new Message("&c&lYour version of PowerLib is outdated!");
            Message m1 = new Message("&a&nClick here to update to v{version}!")
                    .addPlaceHolder("{version}", this.pluginUpdater.getLatestVersion());
            TextComponent update = new TextComponent((m1.getText()));
            update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                    "https://spigotmc.org/resources/83274/download?version=" + this.pluginUpdater.getSpigotVersionId()));
            m.send(player);
            player.spigot().sendMessage(update);
        }
    }
}
