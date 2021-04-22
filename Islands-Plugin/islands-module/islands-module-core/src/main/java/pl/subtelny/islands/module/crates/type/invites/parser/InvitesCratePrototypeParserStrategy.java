package pl.subtelny.islands.module.crates.type.invites.parser;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.islands.module.crates.type.invites.InvitesCratePrototype;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;
import java.util.Map;

public class InvitesCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public InvitesCratePrototypeParserStrategy(File file, String crateKeyPrefix, ItemCrateParserStrategy itemCrateParserStrategy) {
        super(file, crateKeyPrefix, itemCrateParserStrategy);
    }

    @Override
    public CratePrototype load(String path) {
        BasicInformation basicInformation = loadBasicInformation("configuration");
        Map<Integer, ItemCrate> content = loadContent("content");
        ItemStack inviteItemStack = loadInviteItemStack();
        return new InvitesCratePrototype(
                basicInformation.crateKey,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content,
                inviteItemStack
        );
    }

    private ItemStack loadInviteItemStack() {
        return new ItemStackFileParserStrategy(configuration, file).load("invite");
    }

}
