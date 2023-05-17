package it.mycraft.powerlib.bungee;

import it.mycraft.powerlib.bungee.config.BungeeConfigManager;
import it.mycraft.powerlib.bungee.updater.PluginUpdater;
import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.bstats.bungeecord.Metrics;

/**
 * PowerLib Official Source code
 *
 * @author AlbeMiglio, FranFrau
 * https://www.github.com/AlbeMiglio/PowerLib
 */
@Getter
public class PowerLib extends Plugin implements Listener {

    @Getter
    @Setter
    private static PowerLib instance;
    private ConfigManager bungeeConfigManager;
    private PluginUpdater updater;
    private boolean checkForUpdates;
    private BungeeAudiences adventure;

    public void onEnable() {
        registerInstances();
        registerListeners();
        new Metrics(this, 11162);
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public @NonNull BungeeAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public Configuration getConfiguration() {
        return this.bungeeConfigManager.get("config.yml");
    }

    private void registerInstances() {
        setInstance(this);
        this.adventure = BungeeAudiences.create(this);
        this.bungeeConfigManager = new BungeeConfigManager(this);
        this.bungeeConfigManager.create("config.yml");
        this.checkForUpdates = getConfiguration().getBoolean("check-for-updates");
        this.updater = new PluginUpdater(this).setGitHubURL("AlbeMiglio", "PowerLib");
    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new Listener() {
            @EventHandler
            public void onJoin(ServerConnectedEvent event) {
                if(!getConfiguration().getBoolean("check-for-updates")) {
                    return;
                }
                ProxyServer.getInstance().getScheduler().runAsync(instance, () -> {
                    ProxiedPlayer player = event.getPlayer();
                    if (!player.hasPermission("powerlib.update")) {
                        return;
                    }
                    if (updater.needsUpdate()) {
                        Message m = new Message("&c&lYour version of PowerLib is outdated!");
                        Message m1 = new Message("&a&nClick here to update to v{version}!")
                                .addPlaceHolder("{version}", updater.getLatestVersion());
                        TextComponent update = new TextComponent((m1.getText()));
                        update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                "https://github.com/AlbeMiglio/PowerLib/releases/latest"));
                        m.send(player);
                        player.sendMessage(update);
                    }
                });
            }
        });
    }
}
