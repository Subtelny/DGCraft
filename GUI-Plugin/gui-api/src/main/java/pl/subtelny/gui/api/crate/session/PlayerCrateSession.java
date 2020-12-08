package pl.subtelny.gui.api.crate.session;

import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.CrateId;

public interface PlayerCrateSession extends CrateViewer {

    boolean isSameInventory(CrateInventory crateInventory);

    void openCrateInventory();

    void closeCrateInventory();

    CrateId getCrate();

}
