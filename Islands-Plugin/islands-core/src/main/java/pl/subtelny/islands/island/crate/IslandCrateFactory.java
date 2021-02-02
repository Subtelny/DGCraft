package pl.subtelny.islands.island.crate;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;
import java.util.Optional;

@Component
public class IslandCrateFactory {

    public Optional<Crate> prepareCrate(CratePrototype prototype, Map<String, String> data) {
        return null;
    }

}
