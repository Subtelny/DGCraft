package pl.subtelny.crate.type;

import pl.subtelny.crate.api.ContentCrate;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.NoSuchElementException;

public class DefaultCrateCreatorStrategy implements CrateCreatorStrategy<CratePrototype> {

    @Override
    public Crate create(CratePrototype prototype) {
        InventoryInfo inventory = createInventory(prototype);
        return new ContentCrate(prototype.getCrateKey(), prototype.getPermission(), inventory, prototype.getContent());
    }

    @Override
    public CrateType getType() {
        throw new NoSuchElementException("Default creator does not have type");
    }

    private InventoryInfo createInventory(CratePrototype prototype) {
        return InventoryInfo.of(prototype.getTitle(), prototype.getInventorySize());
    }

}
