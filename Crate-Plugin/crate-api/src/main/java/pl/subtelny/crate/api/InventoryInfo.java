package pl.subtelny.crate.api;

public final class InventoryInfo {

    private final String title;

    private final int size;

    private InventoryInfo(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public static InventoryInfo of(String title, int size) {
        return new InventoryInfo(title, size);
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }
}
