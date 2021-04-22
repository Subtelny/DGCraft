package pl.subtelny.islands.module.crates.type.config;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.ItemCrateClickResult;
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ConfigItemCrate implements ItemCrate {

    private final ItemCrate itemCrate;

    private final String key;

    private final String color;

    private final String fadedColor;

    private final List<String> options;

    private int currentOption;

    public ConfigItemCrate(ItemCrate itemCrate,
                           String key,
                           String color,
                           String fadedColor,
                           List<String> options) {
        Validation.isTrue(options.size() > 0, "Options list cannot be empty");
        this.color = color;
        this.fadedColor = fadedColor;
        this.itemCrate = itemCrate;
        this.key = key;
        this.options = options;
        this.currentOption = calculateCurrentOption();
    }

    @Override
    public ItemCrateClickResult click(Player player, CrateData crateData) {
        ItemCrateClickResult result = itemCrate.click(player, crateData);
        if (result.isSuccess()) {
            nextOption();
            updateValue();
            return ItemCrateClickResult.result(prepareItemStack(result), result);
        }
        return result;
    }

    @Override
    public ItemStack getItemStack() {
        return prepareItemStack(itemCrate.getItemStack());
    }

    private ItemStack prepareItemStack(ItemCrateClickResult result) {
        ItemStack resultNewItemStack = result.getNewItemStack();
        if (resultNewItemStack != null) {
            return prepareItemStack(resultNewItemStack);
        }
        return getItemStack();
    }

    private ItemStack prepareItemStack(ItemStack itemStack) {
        Map<String, String> data = getData();
        return ItemStackUtil.prepareItemStack(itemStack, data);
    }

    private Map<String, String> getData() {
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

    private int calculateCurrentOption() {
        String value = configuration.getStringValue(key, BooleanDataType.TYPE);
        return IntStream.rangeClosed(0, options.size() - 1).boxed()
                .filter(slot -> options.get(slot).equals(value))
                .findFirst()
                .orElse(0);
    }

    private void nextOption() {
        if (options.size() - 1 > currentOption) {
            currentOption++;
        } else {
            currentOption = 0;
        }
    }

    private void updateValue() {
        String value = options.get(currentOption);
        configuration.updateValue(key, BooleanDataType.TYPE, value);
    }

}
