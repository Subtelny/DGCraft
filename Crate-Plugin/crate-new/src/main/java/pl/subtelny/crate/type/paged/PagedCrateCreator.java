package pl.subtelny.crate.type.paged;

import org.bukkit.Bukkit;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateCreateRequest;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.crate.type.personal.PersonalCrate;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;
import pl.subtelny.utilities.ColorUtil;

@Component
public class PagedCrateCreator extends ACrateCreator<PagedCratePrototype> {

    @Override
    public Crate createCrate(CrateCreateRequest<PagedCratePrototype> request) {
        PagedCratePrototype cratePrototype = request.getCratePrototype();
        return new PersonalCrate(
                cratePrototype.getCrateId(),
                Bukkit.createInventory(null, cratePrototype.getSize(), ColorUtil.color(cratePrototype.getTitle())),
                request.getCrateData(),
                getUseConditions(cratePrototype),
                cratePrototype.getItemCrates()
        );
    }

}
