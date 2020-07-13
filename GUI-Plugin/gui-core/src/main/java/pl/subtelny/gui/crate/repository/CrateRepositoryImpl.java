package pl.subtelny.gui.crate.repository;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CrateRepositoryImpl implements CrateRepository {

    private Map<CrateId, Crate> crates = new HashMap<>();

    private Map<CrateId, CrateInventory> globalInventories = new HashMap<>();

    @Override
    public Optional<Crate> findCrate(CrateId crateId) {
        return Optional.ofNullable(crates.get(crateId));
    }

    @Override
    public void registerCrate(Crate crate) {
        crates.put(crate.getId(), crate);
    }

    @Override
    public void unregisterCrate(CrateId crateId) {
        crates.remove(crateId);
        globalInventories.remove(crateId);
    }

    @Override
    public void unregisterAll(Plugin plugin) {
        List<CrateId> crateIds = crates.keySet().stream()
                .filter(crateId -> crateId.getPluginName().equals(plugin.getName())).collect(Collectors.toList());
        crateIds.forEach(crateId -> {
            crates.remove(crateId);
            globalInventories.remove(crateId);
        });
    }

    @Override
    public Optional<CrateInventory> findGlobalInventory(CrateId crateId) {
        return Optional.ofNullable(globalInventories.get(crateId));
    }

    @Override
    public void updateGlobalInventory(CrateInventory crateInventory) {
        globalInventories.put(crateInventory.getCrateId(), crateInventory);
    }

}
