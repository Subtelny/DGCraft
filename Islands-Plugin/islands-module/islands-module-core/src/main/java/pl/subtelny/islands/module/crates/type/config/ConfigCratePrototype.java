package pl.subtelny.islands.module.crates.type.config;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;
import java.util.Objects;

public class ConfigCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("CONFIG");

    private final Map<Integer, ConfigItemCratePrototype> configContent;

    public ConfigCratePrototype(CrateKey crateKey,
                                String title,
                                String permission,
                                int size,
                                Map<Integer, ItemCrate> content,
                                Map<Integer, ConfigItemCratePrototype> configContent) {
        super(crateKey, TYPE, title, permission, size, content);
        this.configContent = configContent;
    }

    public Map<Integer, ConfigItemCratePrototype> getConfigContent() {
        return configContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConfigCratePrototype that = (ConfigCratePrototype) o;
        return Objects.equals(configContent, that.configContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), configContent);
    }
}
