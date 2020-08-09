package pl.subtelny.gui.crate;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.repository.CrateInventoryCreator;
import pl.subtelny.gui.crate.settings.CrateSettings;
import pl.subtelny.gui.messages.CrateMessages;

@Component
public class CrateService {

    private final CrateSettings settings;

    private final CrateMessages messages;

    private final CrateInventoryCreator inventoryCreator;

    @Autowired
    public CrateService(CrateSettings settings, CrateMessages messages, CrateInventoryCreator inventoryCreator) {
        this.settings = settings;
        this.messages = messages;
        this.inventoryCreator = inventoryCreator;
    }

    public CrateInventory getInventoryForCrate(Player player, Crate crate) {
        return inventoryCreator.createInv(player, crate);
    }

}
