package pl.subtelny.crate.repository;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CrateRepository {

    private final Map<CrateId, CratePrototype> cratePrototypes = new HashMap<>();

    private final Map<CrateId, Crate> globalCrates = new HashMap<>();

    public Optional<Crate> findGlobalCrate(CrateId crateId) {
        return Optional.ofNullable(globalCrates.get(crateId));
    }

    public Optional<CratePrototype> findCratePrototype(CrateId crateId) {
        return Optional.ofNullable(cratePrototypes.get(crateId));
    }

    public void addCratePrototype(CrateId crateId, CratePrototype cratePrototype) {
        cratePrototypes.put(crateId, cratePrototype);
    }

    public void addGlobalCrate(Crate crate) {
        globalCrates.put(crate.getId(), crate);
    }

    public void removeCratePrototype(CrateId crateId) {
        cratePrototypes.remove(crateId);
    }

    public void removeGlobalCrate(CrateId crateId) {
        cratePrototypes.remove(crateId);
    }

    public void removeCrates(Plugin plugin) {
        cratePrototypes.keySet()
                .stream()
                .filter(crateId -> crateId.getPluginName().equals(plugin.getName()))
                .forEach(cratePrototypes::remove);
        globalCrates.keySet()
                .stream()
                .filter(crateId -> crateId.getPluginName().equals(plugin.getName()))
                .forEach(globalCrates::remove);
    }

}
