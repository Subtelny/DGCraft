package pl.subtelny.islands.island.crate.settings;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.IslandCrateCreator;
import pl.subtelny.islands.island.crate.settings.prototype.IslandSettingsCratePrototype;

import java.util.Map;
import java.util.Set;

@Component
public class IslandSettingsCrateCreator implements IslandCrateCreator<IslandSettingsCratePrototype> {

    private final CrateCreator<PageCratePrototype> crateCreator;

    @Autowired
    public IslandSettingsCrateCreator(CrateCreator<PageCratePrototype> crateCreator) {
        this.crateCreator = crateCreator;
    }

    @Override
    public Crate create(IslandSettingsCratePrototype prototype,
                        Map<String, String> data,
                        Island island) {
        PageCratePrototype pageCratePrototype = getPageCratePrototype(prototype);
        return crateCreator.create(pageCratePrototype, data);
    }

    private PageCratePrototype getPageCratePrototype(IslandSettingsCratePrototype prototype) {
        return new PageCratePrototype(prototype.getCrateId(),
                prototype.getCrateType(),
                prototype.getTitle(),
                prototype.getPermission(),
                prototype.getSize(),
                prototype.getItems(),
                prototype.getPreviousPageItemStack(),
                prototype.getNextPageItemStack(),
                prototype.getStaticItems());
    }

    @Override
    public CrateType getType() {
        return IslandSettingsCratePrototype.SETTINGS_CRATE_TYPE;
    }

}
