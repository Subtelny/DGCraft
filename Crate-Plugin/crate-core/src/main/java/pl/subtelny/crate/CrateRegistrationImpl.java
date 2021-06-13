package pl.subtelny.crate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateRegistration;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.inventory.CrateInventory;

import java.util.List;

@Component
public class CrateRegistrationImpl implements CrateRegistration {

    private final CrateStorage crateStorage = new CrateStorage();

    @Override
    public void unregisterCratePrototype(List<CrateId> crateIds) {
        crateIds.forEach(crateId -> {
            crateStorage.removeCratePrototype(crateId);
            crateStorage.removeSharedCrate(crateId);
        });
        closeAllInventories(crateIds);
    }

    @Override
    public void registerCratePrototype(CratePrototype cratePrototype) {
        crateStorage.addCratePrototype(cratePrototype);
    }

    private void closeAllInventories(List<CrateId> crateIds) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Inventory inventory = onlinePlayer.getOpenInventory().getTopInventory();
            if (inventory instanceof CrateInventory) {
                Crate crate = ((CrateInventory) inventory).getCrate();
                if (crateIds.contains(crate.getCrateId())) {
                    onlinePlayer.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                }
            }
        }
    }
}
