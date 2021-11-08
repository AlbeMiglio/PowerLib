package it.mycraft.powerlib.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.velocity.updater.PluginUpdater;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.nio.file.Path;

@Getter
@Plugin(id = "powerlib", name = "PowerLib", version = "1.2.0-TEST-11", authors = {"AlbeMiglio", "pompiere1"})
public class PowerLib {

    @Getter
    private ProxyServer proxy;
    private Logger logger;
    private Metrics.Factory metricsFactory;
    private PluginDescription description;
    private Path folder;
    @Getter
    private static PowerLib instance;
    private PluginUpdater updater;

    @Inject
    public void init(ProxyServer proxy, PluginDescription description, Logger logger, Metrics.Factory metricsFactory,
                     @DataDirectory Path folder) {
        instance = this;
        this.proxy = proxy;
        this.description = description;
        this.folder = folder;
        this.logger = logger;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        this.updater = new PluginUpdater(description.getVersion().get()).setGitHubURL("AlbeMiglio", "PowerLib");
        Metrics metrics = metricsFactory.make(this, 11190);
    }

    @Subscribe
    public void onServerConnected(ServerPostConnectEvent ev) {
        getProxy().getScheduler().buildTask(this, () -> {
               Player player = ev.getPlayer();
               if (!player.hasPermission("powerlib.update")) {
                   return;
               }
               if (this.updater.needsUpdate()) {
                   Message m = new Message("&c&lYour version of PowerLib is outdated!");
                   Message m1 = new Message("&a&nClick here to update to v{version}!")
                           .addPlaceHolder("{version}", this.updater.getLatestVersion());
                   Component update = Component.text(m1.getText());
                   update.clickEvent(ClickEvent.openUrl("https://github.com/AlbeMiglio/PowerLib/releases/latest"));
                   m.send(player);
                   player.sendMessage(update);
               }
        }).schedule();
    }
}
