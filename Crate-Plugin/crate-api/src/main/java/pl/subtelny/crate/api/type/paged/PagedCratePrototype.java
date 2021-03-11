package pl.subtelny.crate.api.type.paged;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.paged.creator.PagedCrateCreatorRequest;

import java.util.Map;

public class PagedCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("PAGED");

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
        super(crateKey, TYPE, title, permission, inventorySize, content);
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
