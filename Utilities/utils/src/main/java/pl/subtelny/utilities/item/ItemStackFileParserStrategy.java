package pl.subtelny.utilities.item;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.utilities.ColorUtil;
import pl.subtelny.utilities.Pair;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.*;

public class ItemStackFileParserStrategy extends PathAbstractFileParserStrategy<ItemStack> {

    public ItemStackFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public String getPath() {
        return "item";
    }

    @Override
    public ItemStack load(String path) {
        try {
            return createItemStack(path);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw ValidationException.of("Error while creating ItemStack, path " + path + ", " + file.getName(), e);
        }
    }

    @Override
    public Saveable set(String path, ItemStack value) {
        throw new UnsupportedOperationException("Saving ItemStack is not implemented yet");
    }

    private ItemStack createItemStack(String path) {
        Material material = Material.getMaterial(configuration.getString(path + "." + getPath()));
        int amount = configuration.getInt(path + ".amount", 1);
        ItemStack itemStack = new ItemStack(material, amount);
        itemStack.setItemMeta(updateItemMeta(path, itemStack.getItemMeta()));
        getInt(configuration, path + ".data").ifPresent(data -> itemStack.setData(material.getNewData(data.byteValue())));
        return itemStack;
    }

    private ItemMeta updateItemMeta(String path, ItemMeta itemMeta) {
        getInt(configuration, path + ".durability").ifPresent(((Damageable) itemMeta)::setDamage);
        getStringList(configuration, path + ".lore").ifPresent(lore -> itemMeta.setLore(ColorUtil.color(lore)));
        getString(configuration, path + ".name").ifPresent(name -> itemMeta.setDisplayName(ColorUtil.color(name)));
        getBoolean(configuration, path + ".unbreakable").ifPresent(itemMeta::setUnbreakable);
        getStringList(configuration, path + ".flags").ifPresent(flags -> itemMeta.addItemFlags(getItemFlags(flags)));
        getEnchantments(path + ".enchantments").ifPresent(enchantments -> addEnchantments(itemMeta, enchantments));
        return itemMeta;
    }

    private void addEnchantments(ItemMeta itemMeta, List<Pair<Enchantment, Integer>> enchantments) {
        enchantments.forEach(enchantment -> itemMeta.addEnchant(enchantment.getLeft(), enchantment.getRight(), true));
    }

    private Optional<List<Pair<Enchantment, Integer>>> getEnchantments(String path) {
        return getStringList(configuration, path)
                .map(enchantments -> enchantments.stream().map(this::createEnchantment).collect(Collectors.toList()));
    }

    private Pair<Enchantment, Integer> createEnchantment(String rawEnchantment) {
        String[] split = rawEnchantment.split(":");
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(split[0]));
        int level = Integer.parseInt(split[1]);
        return new Pair<>(enchantment, level);
    }

    private ItemFlag[] getItemFlags(List<String> flags) {
        return flags.stream().map(ItemFlag::valueOf).toArray(ItemFlag[]::new);
    }

}
