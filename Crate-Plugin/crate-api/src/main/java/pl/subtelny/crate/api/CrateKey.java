package pl.subtelny.crate.api;

import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class CrateKey {

    private final String identity;

    private final Plugin plugin;

    private CrateKey(String identity, Plugin plugin) {
        this.identity = identity;
        this.plugin = plugin;
    }

    public static CrateKey of(String id, Plugin plugin) {
        return new CrateKey(id, plugin);
    }

    public String getIdentity() {
        return identity;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateKey crateKey = (CrateKey) o;
        return Objects.equals(identity, crateKey.identity) && Objects.equals(plugin, crateKey.plugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, plugin);
    }
}
