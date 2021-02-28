package pl.subtelny.crate.type.reload;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.ItemCrateClickResult;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.Objects;
import java.util.function.Supplier;

public class ReloadItemCrate implements ItemCrate {

    private final ItemCrate itemCrate;

    private final Supplier<ReloadItemCrateData> data;

    private final ReloadType reloadType;

    public ReloadItemCrate(ItemCrate itemCrate, Supplier<ReloadItemCrateData> data, ReloadType reloadType) {
        this.itemCrate = itemCrate;
        this.data = data;
        this.reloadType = reloadType;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        ItemCrateClickResult clickResult = itemCrate.click(player);
        return handleClickResult(clickResult);
    }

    @Override
    public ItemStack getItemStack() {
        return prepareItemStack(itemCrate.getItemStack());
    }

    private ItemCrateClickResult handleClickResult(ItemCrateClickResult result) {
        if (result.isRemoveItem()) {
            return result;
        }
        switch (reloadType) {
            case WHEN_SUCCESS:
                return result.isSuccess() ? recalculateData(result) : result;
            case WHEN_FAILURE:
                return !result.isSuccess() ? recalculateData(result) : result;
            default:
                return recalculateData(result);
        }
    }

    private ItemCrateClickResult recalculateData(ItemCrateClickResult result) {
        ItemStack newItemStack = prepareItemStack(result);
        return ItemCrateClickResult.result(newItemStack, result);
    }

    private ItemStack prepareItemStack(ItemCrateClickResult result) {
        ItemStack newItemStack = result.getNewItemStack();
        if (newItemStack == null) {
            return getItemStack();
        }
        return prepareItemStack(newItemStack);
    }

    private ItemStack prepareItemStack(ItemStack itemStack) {
        ReloadItemCrateData data = this.data.get();
        return ItemStackUtil.prepareItemStack(itemStack, data.getData());
    }

    public enum ReloadType {

        WHEN_SUCCESS,
        WHEN_FAILURE,
        WHEN_BOTH

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReloadItemCrate that = (ReloadItemCrate) o;
        return Objects.equals(itemCrate, that.itemCrate) && reloadType == that.reloadType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCrate, reloadType);
    }
}
