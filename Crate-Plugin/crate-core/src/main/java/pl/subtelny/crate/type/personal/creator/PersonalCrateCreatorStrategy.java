package pl.subtelny.crate.type.personal.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.creator.CrateCreatorStrategy;
import pl.subtelny.crate.type.personal.PersonalCrate;
import pl.subtelny.utilities.ColorUtil;

public class PersonalCrateCreatorStrategy implements CrateCreatorStrategy<PersonalCrateCreatorRequest> {

    @Override
    public Crate create(PersonalCrateCreatorRequest request) {
        Inventory inventory = createInventory(request);
        return new PersonalCrate(request.getCrateKey(), request.getPermission(), inventory, request.getContent());
    }

    @Override
    public CrateType getType() {
        return PersonalCrate.TYPE;
    }

    private Inventory createInventory(PersonalCrateCreatorRequest request) {
        String title = ColorUtil.color(request.getTitle());
        return Bukkit.createInventory(null, request.getInventorySize(), title);
    }

}
