package pl.subtelny.crate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.creator.CrateCreators;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class CrateService {

    private final CrateCreators crateCreator;

    private final CrateStorage crateStorage;

    @Autowired
    public CrateService(CrateCreators crateCreator, CrateStorage crateStorage) {
        this.crateCreator = crateCreator;
        this.crateStorage = crateStorage;
    }

    public void unregisterCratePrototype(CrateId crateId) {
        crateStorage.removeCratePrototype(crateId);
        crateStorage.removeSharedCrate(crateId);
        closeAllInventories(crateId);
    }

    public void registerCratePrototype(CratePrototype cratePrototype) {
        crateStorage.addCratePrototype(cratePrototype);
    }

    public Crate getCrate(CrateId crateId) {
        return getCrate(crateId, CrateData.EMPTY);
    }

    public Crate getCrate(CrateId crateId, CrateData data) {
        return crateStorage.findSharedCrate(crateId)
                .orElseGet(() -> getCrateFromPrototype(crateId, data));
    }

    private Crate getCrateFromPrototype(CrateId crateId, CrateData data) {
        CratePrototype prototype = crateStorage.findCratePrototype(crateId)
                .orElseThrow(() -> ValidationException.of("crateService.crate_not_found", crateId.getInternal()));
        Crate crate = crateCreator.createCrate(prototype, data);
        if (prototype.isShared()) {
            crateStorage.addSharedCrate(crate);
        }
        return crate;
    }

    private void closeAllInventories(CrateId crateId) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Inventory inventory = onlinePlayer.getOpenInventory().getTopInventory();
            if (inventory instanceof CrateInventory) {
                Crate crate = ((CrateInventory) inventory).getCrate();
                if (crate.getCrateId().equals(crateId)) {
                    onlinePlayer.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                }
            }
        }
    }

}
