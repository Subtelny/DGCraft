package pl.subtelny.crate.creator;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.IntegerUtil;
import pl.subtelny.utilities.Validation;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractCratePrototypeCreator<PROTOTYPE extends CratePrototype> implements CratePrototypeCreator<PROTOTYPE> {

    protected abstract ItemCrate getItemCrate(String path);

    protected CrateId getCrateId(File file) {
        String id = file.getName().replace(".yml", "");
        return CrateId.of(id);
    }

    protected boolean isShared(YamlConfiguration configuration, String prePath) {
        return configuration.getBoolean(path(prePath, "configuration.shared"), false);
    }

    protected String getPermission(YamlConfiguration configuration, String prePath) {
        return configuration.getString(path(prePath, "configuration.permission"));
    }

    protected String getTitle(YamlConfiguration configuration, String prePath) {
        return configuration.getString(path(prePath, "configuration.title"));
    }

    protected Map<Integer, ItemCrate> getContent(YamlConfiguration configuration, String path) {
        String contentPath = path(path, "content");
        Set<Integer> slots = getContentSlots(configuration, contentPath);
        return slots.stream()
                .collect(Collectors.toMap(
                        slot -> slot,
                        slot -> getItemCrate(contentPath + "." + slot)
                ));
    }

    protected Set<Integer> getContentSlots(YamlConfiguration configuration, String path) {
        return ConfigUtil.getSectionKeys(configuration, path)
                .orElseGet(HashSet::new)
                .stream()
                .filter(IntegerUtil::isInt)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    protected int getSize(YamlConfiguration configuration, String prePath) {
        int size = configuration.getInt(path(prePath, "configuration.size"), 9);
        Validation.isTrue(size % 9 == 0, "Size of inventory cannot be divided by 0.");
        return size;
    }

    protected String path(String prePath, String path) {
        if (prePath.isEmpty()) {
            return path;
        }
        return prePath + "." + path;
    }

}
