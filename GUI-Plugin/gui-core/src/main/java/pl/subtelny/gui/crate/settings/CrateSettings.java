package pl.subtelny.gui.crate.settings;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;
import java.util.*;

@Component
public class CrateSettings {

    private ItemStack notSatisfiedConditionsItemPattern;

    public void initSettings(Plugin plugin) {
        File config = FileUtil.copyFile(plugin, "config.yml");
        notSatisfiedConditionsItemPattern = new ItemStackFileParserStrategy(config).load("not-satisfied.item");
    }

    public Optional<ItemStack> getNotSatisfiedConditionsItemPattern() {
        return Optional.ofNullable(notSatisfiedConditionsItemPattern);
    }

}
