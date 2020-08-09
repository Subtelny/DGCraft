package pl.subtelny.gui.api.crate.session;

import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;

public interface PlayerCrateSession {

    boolean isSameInventory(CrateInventory crateInventory);

    void openCrateInventory();

    void closeCrateInventory();

    Crate getCrate();

    void click(int slot);

    void refreshSlot(int slot);
}
