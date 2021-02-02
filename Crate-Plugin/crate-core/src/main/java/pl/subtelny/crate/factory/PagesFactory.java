package pl.subtelny.crate.factory;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.model.crate.page.BasicPage;
import pl.subtelny.crate.model.crate.page.Page;
import pl.subtelny.crate.model.item.BasicItemCrate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.PersonalItemCrate;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.reward.Reward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PagesFactory {

    private final CrateMessages messages;

    private final PageCratePrototype cratePrototype;

    private final Map<String, String> data;

    private final Reward nextPageReward;

    private final Reward previousPageReward;

    public PagesFactory(CrateMessages messages,
                        PageCratePrototype cratePrototype,
                        Map<String, String> data,
                        Reward nextPageReward,
                        Reward previousPageReward) {
        this.messages = messages;
        this.cratePrototype = cratePrototype;
        this.data = data;
        this.nextPageReward = nextPageReward;
        this.previousPageReward = previousPageReward;
    }

    public List<Page> getPages() {
        List<Page> pages = new ArrayList<>();
        for (int pageCount = 1; pageCount <= countPages(); pageCount++) {
            Page page = createPage(pageCount);
            pages.add(page);
        }
        return pages;
    }

    private Page createPage(int page) {
        Map<Integer, ItemCrate> items = getItems(page);
        return new BasicPage(items);
    }

    private Map<Integer, ItemCrate> getItems(int page) {
        ItemCrate nextPage = getControllerItemCrate(cratePrototype.getNextPageItemStack(), nextPageReward);
        ItemCrate previousPage = getControllerItemCrate(cratePrototype.getPreviousPageItemStack(), previousPageReward);

        Map<Integer, ItemCrate> items = cratePrototype.getItems().entrySet()
                .stream()
                .filter(entry -> matchIntoPage(entry.getKey(), page))
                .collect(
                        Collectors.toMap(
                                entry -> computeSlotForPage(entry.getKey(), page),
                                entry -> toItemCrate(entry.getValue()))
                );
        if (page > 1) {
            items.put(0, previousPage);
        }
        if (countPages() > page) {
            items.put(8, nextPage);
        }
        return items;
    }

    private ItemCrate getControllerItemCrate(ItemStack itemStack, Reward reward) {
        ItemStack prepareItemStack = ItemStackUtil.prepareItemStack(itemStack, data);
        List<Reward> rewards = Collections.singletonList(reward);
        ItemCratePrototype controllerPrototype = new ItemCratePrototype(prepareItemStack, false, false, Collections.emptyList(), Collections.emptyList(), rewards);
        return new BasicItemCrate(prepareItemStack, controllerPrototype);
    }

    private int computeSlotForPage(int slot, int page) {
        int invSize = cratePrototype.getSize();
        int odd = (invSize * page) - invSize;
        return slot - odd;
    }

    private boolean matchIntoPage(int slot, int page) {
        int invSize = cratePrototype.getSize();
        int slotTo = page * invSize;
        int slotFrom = slotTo - invSize;
        return slot >= slotFrom && slot < slotTo;
    }

    private int countPages() {
        int invSize = cratePrototype.getSize();
        int lastItemSlot = lastItemSlot();
        double result = (double) lastItemSlot / invSize;
        return (int) Math.ceil(result);
    }

    private int lastItemSlot() {
        return cratePrototype.getItems().keySet()
                .stream()
                .max(Integer::compare)
                .orElse(0);
    }

    private ItemCrate toItemCrate(ItemCratePrototype prototype) {
        ItemStack itemStack = prototype.getItemStack();
        ItemStack preparedItemStack = ItemStackUtil.prepareItemStack(itemStack, data);
        return new PersonalItemCrate(preparedItemStack, prototype, messages);
    }
}
