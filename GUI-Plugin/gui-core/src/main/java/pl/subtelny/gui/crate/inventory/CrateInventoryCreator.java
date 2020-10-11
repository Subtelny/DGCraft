package pl.subtelny.gui.crate.inventory;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.crate.model.Crate;
import pl.subtelny.gui.crate.repository.CrateRepository;
import pl.subtelny.utilities.Validation;

@Component
public class CrateInventoryCreator {

    private final CrateRepository crateRepository;

    @Autowired
    public CrateInventoryCreator(CrateRepository crateRepository) {
        this.crateRepository = crateRepository;
    }

    public CrateInventory createInv(Crate crate) {
        if (crate.isGlobal()) {
            return computeGlobalCrateInventory(crate);
        }
        return computeCrateInventory(null, crate);
    }

    public CrateInventory createInv(Player player, Crate crate) {
        if (crate.isGlobal()) {
            return createInv(crate);
        }
        return computeCrateInventory(player, crate);
    }

    private CrateInventory computeGlobalCrateInventory(Crate crate) {
        return crateRepository.findGlobalInventory(crate.getId()).orElse(createGlobalCrateInventory(crate));
    }

    private CraftCrateInventory createGlobalCrateInventory(Crate crate) {
        Validation.isTrue(crate.isGlobal(), "Crate should be global");
        CraftCrateInventory crateInventory = CraftCrateInventory.create(crate.getId(), crate.getSize(), crate.getTitle());
        fillCrateInventory(crateInventory, crate);
        crateRepository.updateGlobalInventory(crateInventory);
        return crateInventory;
    }

    private CraftCrateInventory computeCrateInventory(Player player, Crate crate) {
        Validation.isTrue(!crate.isGlobal(), "Crate shouldnt be global");
        CraftCrateInventory crateInventory = CraftCrateInventory.create(crate.getId(), player, crate.getSize(), crate.getTitle());
        fillCrateInventory(crateInventory, crate);
        return crateInventory;
    }

    private void fillCrateInventory(CraftCrateInventory crateInventory, Crate crate) {
        crate.getItems().forEach((slot, itemCrate) -> crateInventory.setItem(slot, itemCrate.getOriginalItemStack()));
    }

}
