package it.mycraft.powerlib.bungee.updater;

import it.mycraft.powerlib.common.objects.enums.SiteType;
import it.mycraft.powerlib.common.utils.JSONUtils;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import javax.json.JsonObject;

public class PluginUpdater {

    @Getter
    private String url;
    private Plugin plugin;
    private String field;

    @Getter
    private long spigotVersionId;

    @Getter
    private SiteType type;

    @Getter
    private String latestVersion;

    public PluginUpdater(Plugin plugin) {
        this.plugin = plugin;
        this.url = "";
        this.latestVersion = "";
    }

    public PluginUpdater setGitHubURL(String user, String repo) {
        this.url = "https://api.github.com/repos/{user}/{repo}/releases/latest"
                .replace("{user}", user)
                .replace("{repo}", repo);
        this.type = SiteType.GITHUB;
        this.field = "tag_name";
        return this;
    }

    public PluginUpdater setSpigotURL(String resourceId) {
        this.url = "https://api.spiget.org/v2/resources/{resourceId}/versions/latest"
                .replace("{resourceId}", resourceId);
        this.type = SiteType.SPIGOTMC;
        this.field = "name";
        return this;
    }

    public PluginUpdater setSpigotURL(int resourceId) {
        return this.setSpigotURL(String.valueOf(resourceId));
    }

    public PluginUpdater setCustomURL(String url, String field) {
        this.url = url;
        this.type = SiteType.OTHER;
        this.field = field;
        return this;
    }

    public boolean needsUpdate() {
        String version = this.plugin.getDescription().getVersion();
        if (JSONUtils.isValidJSON(this.url)) {
            JsonObject obj = JSONUtils.getJSON(url);
            try {
                this.latestVersion = obj.getString(this.field);
            } catch(NullPointerException ex) {
                return false;
            }
            if (type == SiteType.SPIGOTMC) {
                this.spigotVersionId = obj.getInt("id");
            }
            return !version.equals(latestVersion);
        }
        return false;
    }
}

