package pl.subtelny.crate.item.config;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateClickResult;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class ConfigItemCrate implements ItemCrate {

    private final ItemCrate itemCrate;

    private final String key;

    private final String color;

    private final String fadedColor;

    private final List<String> options;

    private int currentOption = -1;

    public ConfigItemCrate(ItemCrate itemCrate, String key, String color, String fadedColor, List<String> options) {
        Validation.isFalse(options.isEmpty(), "Options list cannot be empty");
        this.itemCrate = itemCrate;
        this.key = key;
        this.color = color;
        this.fadedColor = fadedColor;
        this.options = options;
    }

    @Override
    public ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData) {
        ItemCrateClickResult result = itemCrate.click(player, actionType, crateData);
        if (result.isSuccess()) {
            return nextOption(crateData, result);
        }
        return result;
    }

    @Override
    public ItemStack getItemStack() {
        return itemCrate.getItemStack();
    }

    @Override
    public ItemStack getItemStack(CrateData crateData) {
        Configuration configuration = getConfig(crateData);
        return getItemStack(configuration, crateData);
    }

    private ItemStack getItemStack(Configuration configuration, CrateData crateData) {
        ItemStack itemStack = itemCrate.getItemStack(crateData);
        return ItemStackUtil.prepareItemStack(itemStack, getData(configuration));
    }

    private ItemCrateClickResult nextOption(CrateData crateData, ItemCrateClickResult result) {
        Configuration configuration = getConfig(crateData);
        int currentOption = getCurrentOption(configuration);
        if (options.size() > currentOption + 1) {
            this.currentOption++;
        } else {
            this.currentOption = 0;
        }
        ItemStack itemStack = getItemStack(configuration, crateData);
        return getItemCrateClickResult(itemStack, result);
    }

    private ItemCrateClickResult getItemCrateClickResult(ItemStack itemStack, ItemCrateClickResult result) {
        return new ItemCrateClickResult(
                itemStack,
                true,
                result.isCloseCrate(),
                result.getNotSatisfiedConditions()
        );
    }

    private Configuration getConfig(CrateData crateData) {
        return crateData.getData("config", Configuration.class);
    }

    private Map<String, String> getData(Configuration configuration) {
        int currentOption = getCurrentOption(configuration);
        Map<String, String> data = new HashMap<>();
        for (int i = 0; i < options.size(); i++) {
            if (i == currentOption) {
                data.put("[OPTION" + i + "]", "&" + color);
            } else {
                data.put("[OPTION" + i + "]", "&" + fadedColor);
            }
        }
        return data;
    }

    private int getCurrentOption(Configuration configuration) {
        if (currentOption == -1) {
            currentOption = calculateCurrentOption(configuration);
        }
        return currentOption;
    }

    private int calculateCurrentOption(Configuration configuration) {
        Optional<String> valueOpt = configuration.findValue(key);
        if (valueOpt.isEmpty()) {
            return 0;
        }
        String value = valueOpt.get();
        return IntStream.rangeClosed(0, options.size() - 1).boxed()
                .filter(slot -> options.get(slot).equals(value))
                .findFirst()
                .orElse(0);
    }

}
