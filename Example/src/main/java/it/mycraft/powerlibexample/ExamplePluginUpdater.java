package it.mycraft.powerlibexample;

import it.mycraft.powerlib.bukkit.updater.PluginUpdater;
import it.mycraft.powerlib.common.chat.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePluginUpdater extends JavaPlugin implements Listener {

    private PluginUpdater pluginUpdater;
    private String resourceId = "83274";

    public void onEnable() {
        this.pluginUpdater = new PluginUpdater(this).setSpigotURL(resourceId);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp() && !player.hasPermission("my.permission")) {
            return;
        }
        if (this.pluginUpdater.needsUpdate()) {
            Message m = new Message("&c&lYour version of PowerLib is outdated! Click here to update to v{version}!")
                    .addPlaceHolder("{version}", this.pluginUpdater.getLatestVersion());
            TextComponent update = new TextComponent(m.getText());
            update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                    "https://spigotmc.org/resources/{resourceId}}/download?version="
                    .replace("{resourceId}", resourceId)
                            + this.pluginUpdater.getSpigotVersionId()));
            player.spigot().sendMessage(update);
        }
    }
}
