package pl.subtelny.crate.type.paged;

import pl.subtelny.crate.api.CrateData;
import pl.subtelny.utilities.Validation;

import java.util.Optional;

public class PagedCrateData {

    private final CrateData crateData;

    private PagedCrateData(CrateData crateData) {
        this.crateData = crateData;
    }

    public static PagedCrateData of(CrateData crateData) {
        return new PagedCrateData(crateData);
    }

    public void setPage(int page) {
        Validation.isTrue(page >= 0, "Page cannot be less than 0");
        crateData.addData("pagedNumber", page);
    }

    public void setPagedCrate(PagedCrate pagedCrate) {
        crateData.addData("pagedCrate", pagedCrate);
    }

    public int getPage() {
        Optional<Integer> pagedNumberOpt = crateData.findData("pagedNumber");
        return pagedNumberOpt
                .orElseThrow(() -> new IllegalStateException("Number of page not found"));
    }

    public PagedCrate getPagedCrate() {
        Optional<PagedCrate> pagedCrate = crateData.findData("pagedCrate");
        return pagedCrate
                .orElseThrow(() -> new IllegalStateException("PagedCrate not found"));
    }

}
