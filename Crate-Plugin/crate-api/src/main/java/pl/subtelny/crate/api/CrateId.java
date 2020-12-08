package pl.subtelny.crate.api;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.identity.CompoundIdentity;

public class CrateId extends CompoundIdentity {

    private final static int PLUGIN_NAME_POSITION = 0;

    private final static int IDENTITY = 1;

    public CrateId(Plugin plugin, String id) {
        super(CompoundIdentity.values(plugin.getName(), id));
    }

    public static CrateId of(Plugin plugin, String value) {
        return new CrateId(plugin, value);
    }

    public String getPluginName() {
        return getAtPosition(PLUGIN_NAME_POSITION);
    }

    public String getIdentity() {
        return getAtPosition(IDENTITY);
    }
}
