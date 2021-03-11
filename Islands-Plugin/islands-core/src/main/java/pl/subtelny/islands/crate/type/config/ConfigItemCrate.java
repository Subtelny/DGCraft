package pl.subtelny.islands.crate.type.config;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.ItemCrateClickResult;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.configuration.ConfigurationValue;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ConfigItemCrate implements ItemCrate {

    private final ItemCrate itemCrate;

    private final ConfigurationKey key;

    private final Configuration configuration;

    private final List<ConfigurationValue> options;

    private int currentOption;

    public ConfigItemCrate(ItemCrate itemCrate,
                           ConfigurationKey key,
                           Configuration configuration,
                           List<ConfigurationValue> options) {
        Validation.isTrue(options.size() > 0, "Options list cannot be empty");
        this.itemCrate = itemCrate;
        this.key = key;
        this.configuration = configuration;
        this.options = options;
        this.currentOption = calculateCurrentOption();
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        ItemCrateClickResult result = itemCrate.click(player);
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
                data.put("[OPTION" + i + "]", "&e");
            } else {
                data.put("[OPTION" + i + "]", "&7");
            }
        }
        return data;
    }

    private int calculateCurrentOption() {
        ConfigurationValue value = configuration.findConfigurationValue(key).orElse(null);
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
        ConfigurationValue value = options.get(currentOption);
        configuration.updateValue(key, value);
    }

}
