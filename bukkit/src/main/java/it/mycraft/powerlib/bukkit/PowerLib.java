package it.mycraft.powerlib.bukkit;

import it.mycraft.powerlib.bukkit.config.BukkitConfigManager;
import it.mycraft.powerlib.bukkit.updater.PluginUpdater;
import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
@Getter
public class PowerLib extends JavaPlugin implements Listener {

    @Getter
    @Setter
    private static PowerLib instance;
    private ConfigManager bukkitConfigManager;
    private PluginUpdater updater;
    private boolean checkForUpdates;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        registerInstances();
        registerListeners();
        new Metrics(this, 11161);
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public Configuration getConfiguration() {
        return this.bukkitConfigManager.get("config.yml");
    }

    private void registerInstances() {
        setInstance(this);
        this.adventure = BukkitAudiences.create(this);
        this.bukkitConfigManager = new BukkitConfigManager(this);
        this.bukkitConfigManager.create("config.yml");
        this.checkForUpdates = getConfiguration().getBoolean("check-for-updates");
        this.updater = new PluginUpdater(this).setGitHubURL("AlbeMiglio", "PowerLib");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                if(!checkForUpdates) {
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                    Player player = event.getPlayer();
                    if (!player.isOp() && !player.hasPermission("powerlib.update")) {
                        return;
                    }
                    if (PowerLib.getInstance().getUpdater().needsUpdate()) {
                        Message m = new Message("&c&lYour version of PowerLib is outdated!");
                        Message m1 = new Message("&a&nClick here to update to v{version}!")
                                .addPlaceHolder("{version}", updater.getLatestVersion());
                        TextComponent update = new TextComponent((m1.getText()));
                        update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                "https://github.com/AlbeMiglio/PowerLib/releases/latest"));
                        m.send(player);
                        player.spigot().sendMessage(update);
                    }
                });
            }
        }, this);
    }
}
