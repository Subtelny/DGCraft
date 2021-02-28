package pl.subtelny.crate.parser;

import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CratePrototypeParserStrategy extends AbstractFileParserStrategy<CratePrototype> {

    private final String crateKeyPrefix;

    private final ItemCrateParserStrategy itemCrateParserStrategy;

    public CratePrototypeParserStrategy(File file, String crateKeyPrefix, ItemCrateParserStrategy itemCrateParserStrategy) {
        super(file);
        this.crateKeyPrefix = crateKeyPrefix;
        this.itemCrateParserStrategy = itemCrateParserStrategy;
    }

    @Override
    public CratePrototype load(String path) {
        BasicInformation basicInformation = loadBasicInformation(path + ".configuration");
        Map<Integer, ItemCrate> content = loadContent(path + ".content");
        return new CratePrototype(
                basicInformation.crateKey,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content
        );
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        throw new UnsupportedOperationException("Saving CratePrototype is not implemented yet");
    }

    protected Map<Integer, ItemCrate> loadContent(String path) {
        Set<String> sections = ConfigUtil.getSectionKeys(configuration, path).orElseGet(HashSet::new);
        return sections.stream()
                .map(this::parseToInt)
                .collect(Collectors.toMap(slot -> slot, slot -> loadItemCrate(path + ".slot")));
    }

    private int parseToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Cannot parse " + value + " + into int");
        }
    }

    private ItemCrate loadItemCrate(String path) {
        return itemCrateParserStrategy.load(path);
    }

    protected BasicInformation loadBasicInformation(String path) {
        String permission = configuration.getString(path + ".permission");
        String title = configuration.getString(path + ".title");
        int inventorySize = getInventorySize(path);
        CrateKey crateKey = loadCrateKey(path);
        return new BasicInformation(permission, title, inventorySize, crateKey);
    }

    private int getInventorySize(String path) {
        int inventorySize = ConfigUtil.getInt(configuration, path + ".size")
                .orElseThrow(() -> new IllegalStateException("Not found inventory size of crate"));
        Validation.isTrue(inventorySize % 9 == 0, "Value " + inventorySize + " is not dividable by 9");
        return inventorySize;
    }

    private CrateKey loadCrateKey(String path) {
        String rawName = file.getName();
        String name = rawName.replace(".yml", "");
        String rawType = ConfigUtil.getString(configuration, path + ".type")
                .orElseThrow(() -> new IllegalStateException("Not found type of crate"));
        CrateType crateType = CrateType.of(rawType.toUpperCase());
        return CrateKey.of(crateType, crateKeyPrefix + "" + name);
    }

    public static final class BasicInformation {

        public final String permission;

        public final String title;

        public final int inventorySize;

        public final CrateKey crateKey;

        private BasicInformation(String permission, String title, int inventorySize, CrateKey crateKey) {
            this.permission = permission;
            this.title = title;
            this.inventorySize = inventorySize;
            this.crateKey = crateKey;
        }
    }

}
