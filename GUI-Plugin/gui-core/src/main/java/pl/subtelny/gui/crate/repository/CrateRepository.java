package pl.subtelny.gui.crate.repository;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.crate.model.Crate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CrateRepository {

    private final Map<CrateId, Crate> crates = new HashMap<>();

    private final Map<CrateId, CrateInventory> globalInventories = new HashMap<>();

    public Optional<Crate> findCrate(CrateId crateId) {
        return Optional.ofNullable(crates.get(crateId));
    }

    public void addCrate(Crate crate) {
        crates.put(crate.getId(), crate);
    }

    public void removeCrate(CrateId crateId) {
        crates.remove(crateId);
        globalInventories.remove(crateId);
    }

    public void removeAll(Plugin plugin) {
        List<CrateId> crateIds = crates.keySet().stream()
                .filter(crateId -> crateId.getPluginName().equals(plugin.getName())).collect(Collectors.toList());
        crateIds.forEach(crateId -> {
            crates.remove(crateId);
            globalInventories.remove(crateId);
        });
    }

    public Optional<CrateInventory> findGlobalInventory(CrateId crateId) {
        return Optional.ofNullable(globalInventories.get(crateId));
    }

    public void updateGlobalInventory(CrateInventory crateInventory) {
        globalInventories.put(crateInventory.getCrateId(), crateInventory);
    }

}
