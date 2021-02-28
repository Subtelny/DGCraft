package pl.subtelny.crate.type.basic.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.creator.CrateCreatorStrategy;
import pl.subtelny.crate.type.basic.BasicCrate;
import pl.subtelny.utilities.ColorUtil;

@Component
public class BasicCrateCreatorStrategy implements CrateCreatorStrategy<BasicCrateCreatorRequest> {

    @Override
    public Crate create(BasicCrateCreatorRequest request) {
        String title = ColorUtil.color(request.getTitle());
        Inventory inventory = Bukkit.createInventory(null, request.getInventorySize(), title);
        return new BasicCrate(request.getCrateKey(), request.getPermission(), request.getContent(), inventory);
    }

    @Override
    public CrateType getType() {
        return BasicCrate.TYPE;
    }

}
