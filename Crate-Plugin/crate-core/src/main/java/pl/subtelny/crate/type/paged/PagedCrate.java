package pl.subtelny.crate.type.paged;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.click.CrateClickResult;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.ArrayList;
import java.util.List;

public class PagedCrate implements Crate {

    private final CrateId crateId;

    private final List<Crate> pages = new ArrayList<>();

    public PagedCrate(CrateId crateId) {
        this.crateId = crateId;
    }

    @Override
    public void open(Player player) {
        Validation.isFalse(pages.isEmpty(), "PagedCrate should have at least one page");
        openPage(player, 0);
    }

    @Override
    public CrateClickResult click(Player player, ActionType actionType, int slot) {
        throw new UnsupportedOperationException("Click is unsupported for paged crate");
    }

    @Override
    public CrateId getCrateId() {
        return crateId;
    }

    @Override
    public CrateData getCrateData() {
        return CrateData.empty();
    }

    @Override
    public boolean isShared() {
        return false;
    }

    @Override
    public boolean addItemCrate(ItemCrate itemCrate) {
        for (Crate page : pages) {
            if (page.addItemCrate(itemCrate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setItemCrate(int slot, ItemCrate itemCrate) {
        throw new UnsupportedOperationException("Set item is unsupported for paged crate");
    }

    public List<Crate> getPages() {
        return new ArrayList<>(pages);
    }

    public int totalOfPages() {
        return pages.size();
    }

    public void addPage(Crate crate) {
        CrateData crateData = crate.getCrateData();
        PagedCrateData pagedCrateData = PagedCrateData.of(crateData);
        pagedCrateData.setPage(pages.size() - 1);
        pages.add(crate);
    }

    public void openNextPage(Player player, int currentPage) {
        openPage(player, currentPage + 1);
    }

    public void openPreviousPage(Player player, int currentPage) {
        openPage(player, currentPage - 1);
    }

    private void openPage(Player player, int page) {
        Validation.isTrue(page >= 0, "Page cannot be less than 0");
        Validation.isTrue(page < pages.size(), "Not found page " + page);
        Crate crate = pages.get(page);
        if (crate != null) {
            crate.open(player);
        }
    }

    private int getCurrentPage(Crate currentCratePage) {
        int currentPage = 0;
        for (int page = 0; page < pages.size(); page++) {
            if (pages.get(page).equals(currentCratePage)) {
                currentPage = page;
            }
        }
        return currentPage;
    }

}
