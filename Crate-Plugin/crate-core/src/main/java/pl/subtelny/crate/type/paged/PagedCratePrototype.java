package pl.subtelny.crate.type.paged;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.creator.CrateCreatorRequest;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.crate.type.paged.creator.PagedCrateCreatorRequest;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class PagedCratePrototype extends CratePrototype {

    private final ItemStack nextPageItemStack;

    private final ItemStack previousPageItemStack;

    private final Map<Integer, ItemCrate> staticContent;

    public PagedCratePrototype(CrateKey crateKey,
                               String title,
                               String permission,
                               int inventorySize,
                               Map<Integer, ItemCrate> content,
                               Map<Integer, ItemCrate> staticContent,
                               ItemStack nextPageItemStack,
                               ItemStack previousPageItemStack) {
        super(crateKey, title, permission, inventorySize, content);
        Validation.isTrue(PagedCrate.TYPE.equals(crateKey.getType()), "CrateKey has not PAGED type");
        this.staticContent = staticContent;
        this.nextPageItemStack = nextPageItemStack;
        this.previousPageItemStack = previousPageItemStack;
    }

    @Override
    public CrateCreatorRequest toCrateCreatorRequest() {
        return PagedCrateCreatorRequest.builder(getCrateKey(), getSize(), nextPageItemStack, previousPageItemStack)
                .setPermission(getPermission())
                .setTitle(getTitle())
                .setContent(getContent())
                .setStaticContent(staticContent)
                .build();
    }
}
