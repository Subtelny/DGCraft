package pl.subtelny.crate.inventory;

public class CraftCrateInventory {

    private final String name;

    private final int size;

    public CraftCrateInventory(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
