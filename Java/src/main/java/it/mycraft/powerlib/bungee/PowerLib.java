package it.mycraft.powerlib.bungee;

import it.mycraft.powerlib.bungee.updater.PluginUpdater;
import it.mycraft.powerlib.common.chat.Message;
import lombok.Getter;
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
 * @authors AlbeMiglio, pompiere1
 * https://www.github.com/AlbeMiglio/PowerLib
 */
public class PowerLib extends Plugin implements Listener {

    @Getter
    private static PowerLib instance;

    private PluginUpdater updater;

    public void onEnable() {
        instance = this;
        this.updater = new PluginUpdater(this).setGitHubURL("AlbeMiglio", "PowerLib");
        getProxy().getPluginManager().registerListener(this, this);
        Metrics metrics = new Metrics(this, 11162);
    }

    @EventHandler
    public void onJoin(ServerConnectedEvent event) {
        ProxyServer.getInstance().getScheduler().runAsync(this, () -> {
            ProxiedPlayer player = event.getPlayer();
            if (!player.hasPermission("powerlib.update")) {
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
                player.sendMessage(update);
            }
        });
    }
}
