package pl.subtelny.crate.type.personal;

import org.bukkit.Bukkit;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateCreateRequest;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.utilities.ColorUtil;

@Component
public class PersonalCrateCreator extends ACrateCreator<PersonalCratePrototype> {

    @Override
    public Crate createCrate(CrateCreateRequest<PersonalCratePrototype> request) {
        PersonalCratePrototype cratePrototype = request.getCratePrototype();
        return new PersonalCrate(
                cratePrototype.getCrateId(),
                Bukkit.createInventory(null, cratePrototype.getSize(), ColorUtil.color(cratePrototype.getTitle())),
                request.getCrateData(),
                getUseConditions(cratePrototype),
                cratePrototype.getItemCrates()
        );
    }

}
