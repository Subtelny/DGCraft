package pl.subtelny.crate.type.paged;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.subtelny.crate.AbstractCrate;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class PagedCrate extends AbstractCrate {

    public static CrateType TYPE = CrateType.of("PAGED");

    private AbstractCrate[] pages;

    private int currentPage = 0;

    public PagedCrate(CrateKey crateKey, Inventory inventory) {
        super(crateKey, permission, new HashSet<>(), inventory);
        Validation.isTrue(TYPE.equals(crateKey.getType()), "");
    }

    @Override
    public Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return pages[currentPage].getItemCrateAtSlot(slot);
    }

    @Override
    public Map<Integer, ItemCrate> getContent() {
        return pages[currentPage].getContent();
    }

    @Override
    public void open(Player player) {
        Validation.isTrue(viewers.isEmpty(), "Only one player can watch paged crate");
        super.open(player);
    }

    public void setPages(AbstractCrate[] pages) {
        this.pages = pages;
    }

    public void nextPage() {
        int nextPage = currentPage + 1;
        changePage(nextPage);
    }

    public void previousPage() {
        int previousPage = currentPage - 1;
        changePage(previousPage);
    }

    private void changePage(int newPage) {
        Validation.isTrue(pages.length > newPage && newPage >= 0, "Not found page " + newPage + " of total pages " + pages.length);
        currentPage = newPage;
        render();
    }

    @Override
    public void render() {
        AbstractCrate page = pages[currentPage];
        page.render();
        inventory.setStorageContents(page.getInventory().getStorageContents());
    }

    @Override
    public void addItemCrate(ItemCrate itemCrate) {
        //Noop
    }

    @Override
    public void removeItemCrate(int slot) {
        pages[currentPage].removeItemCrate(slot);
    }
}
