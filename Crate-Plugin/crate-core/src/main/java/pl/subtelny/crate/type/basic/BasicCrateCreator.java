package pl.subtelny.crate.type.basic;

import org.bukkit.Bukkit;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.BaseCrate;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateCreateRequest;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.utilities.ColorUtil;

@Component
public class BasicCrateCreator extends ACrateCreator<BasicCratePrototype> {

    @Override
    public Crate createCrate(CrateCreateRequest<BasicCratePrototype> request) {
        BasicCratePrototype cratePrototype = request.getCratePrototype();
        return new BaseCrate(
                cratePrototype.getCrateId(),
                Bukkit.createInventory(null, cratePrototype.getSize(), ColorUtil.color(cratePrototype.getTitle())),
                request.getCrateData(),
                getUseConditions(cratePrototype),
                cratePrototype.getItemCrates(),
                cratePrototype.isShared());
    }

}
