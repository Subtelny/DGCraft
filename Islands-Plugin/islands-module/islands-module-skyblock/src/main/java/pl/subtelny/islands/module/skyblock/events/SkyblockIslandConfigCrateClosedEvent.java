package pl.subtelny.islands.module.skyblock.events;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.islands.event.IslandEvent;

public class SkyblockIslandConfigCrateClosedEvent implements IslandEvent {

    private final Crate crate;

    public SkyblockIslandConfigCrateClosedEvent(Crate crate) {
        this.crate = crate;
    }

    public Crate getCrate() {
        return crate;
    }
}
