package pl.subtelny.crate.api.service;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;

public final class InitializeCratesRequest {

    private final File dir;

    private final Plugin plugin;

    public final String prefixKey;

    public InitializeCratesRequest(File dir, Plugin plugin, String prefixKey) {
        this.dir = dir;
        this.plugin = plugin;
        this.prefixKey = prefixKey;
    }

    public static InitializeCratesRequest of(File dir, Plugin plugin, String prefixCrateKey) {
        return new InitializeCratesRequest(dir, plugin, prefixCrateKey);
    }

    public File getDir() {
        return dir;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getPrefixKey() {
        return prefixKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitializeCratesRequest that = (InitializeCratesRequest) o;
        return Objects.equals(dir, that.dir) && Objects.equals(plugin, that.plugin) && Objects.equals(prefixKey, that.prefixKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dir, plugin, prefixKey);
    }
}
