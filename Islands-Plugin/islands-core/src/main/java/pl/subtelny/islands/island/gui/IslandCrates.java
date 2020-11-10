package pl.subtelny.islands.island.gui;

import pl.subtelny.gui.api.crate.model.CrateId;

public interface IslandCrates {

    CrateId getMainCrate();

    void reloadCrates();

}
