package it.mycraft.powerlib.updater;

import it.mycraft.powerlib.utils.JSONUtils;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class PluginUpdater {

    @Getter
    private String url;
    private Plugin plugin;
    private String field;

    @Getter
    @Nullable
    private String spigotVersionId;

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
            JSONObject obj = JSONUtils.getJSON(url);
            this.latestVersion = obj.getString(this.field);
            if(type == SiteType.SPIGOTMC) {
                this.spigotVersionId = obj.getString("id");
            }
            return !version.equals(latestVersion);
        }
        return false;
    }
}
