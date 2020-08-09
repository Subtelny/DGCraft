package pl.subtelny.gui.api.crate.repository;

import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;

import java.util.Optional;

public interface CrateRepository {

    Optional<Crate> findInternalCrate(String crateIdentity);

    Optional<Crate> findCrate(CrateId crateId);

    void registerCrate(Crate crate);

    void unregisterCrate(CrateId crateId);

    void unregisterAll(Plugin plugin);

    void updateGlobalInventory(CrateInventory crateInventory);

    Optional<CrateInventory> findGlobalInventory(CrateId crateId);
}
