package pl.subtelny.crate.factory;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.model.crate.global.GlobalCrate;
import pl.subtelny.crate.model.item.BasicItemCrate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GlobalCrateCreator implements CrateCreator<CratePrototype> {

    @Override
    public Crate create(CratePrototype prototype, Map<String, String> data) {
        Map<Integer, ItemCrate> itemCrates = getItemCrates(prototype.getItems(), data);
        return new GlobalCrate(prototype, itemCrates);
    }

    private Map<Integer, ItemCrate> getItemCrates(Map<Integer, ItemCratePrototype> prototypes, Map<String, String> data) {
        return prototypes.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toItemCrate(entry.getValue(), data)));
    }

    private ItemCrate toItemCrate(ItemCratePrototype prototype, Map<String, String> data) {
        ItemStack itemStack = prototype.getItemStack();
        ItemStack preparedItemStack = ItemStackUtil.prepareItemStack(itemStack, data);
        return new BasicItemCrate(preparedItemStack, prototype);
    }

    @Override
    public CrateType getType() {
        return GlobalCrate.GLOBAL_TYPE;
    }
}
