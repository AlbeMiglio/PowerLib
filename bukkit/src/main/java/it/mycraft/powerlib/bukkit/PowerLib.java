package it.mycraft.powerlib.bukkit;

import it.mycraft.powerlib.bukkit.config.BukkitConfigManager;
import it.mycraft.powerlib.bukkit.updater.PluginUpdater;
import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PowerLib Official Source code
 *
 * @author AlbeMiglio, FranFrau
 * https://www.github.com/AlbeMiglio/PowerLib
 */
public class PowerLib extends JavaPlugin implements Listener {

    @Getter
    @Setter
    private static PowerLib instance;

    @Getter
    private BukkitConfigManager bukkitConfigManager;

    private PluginUpdater updater;

    public void onEnable() {
        setInstance(this);
        this.bukkitConfigManager = new BukkitConfigManager(this);
        this.bukkitConfigManager.create("config.yml");
        this.updater = new PluginUpdater(this).setGitHubURL("AlbeMiglio", "PowerLib");
        Bukkit.getPluginManager().registerEvents(this, this);
        new Metrics(this, 11161);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!getConfiguration().getBoolean("check-for-updates")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            Player player = event.getPlayer();
            if (!player.isOp() && !player.hasPermission("powerlib.update")) {
                return;
            }
            if (this.updater.needsUpdate()) {
                Message m = new Message("&c&lYour version of PowerLib is outdated!");
                Message m1 = new Message("&a&nClick here to update to v{version}!")
                        .addPlaceHolder("{version}", this.updater.getLatestVersion());
                TextComponent update = new TextComponent((m1.getText()));
                update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                        "https://github.com/AlbeMiglio/PowerLib/releases/latest"));
                m.send(player);
                player.spigot().sendMessage(update);
            }
        });
    }

    public Configuration getConfiguration() {
        return this.bukkitConfigManager.get("config.yml");
    }
}
