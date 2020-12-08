package pl.subtelny.crate.factory;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.model.crate.page.BasicPage;
import pl.subtelny.crate.model.crate.page.Page;
import pl.subtelny.crate.model.crate.page.PageCrate;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.crate.model.item.BasicItemCrate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.model.item.PersonalItemCrate;
import pl.subtelny.utilities.reward.Reward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PageCrateCreator extends AbstractCrateCreator implements CrateCreator<PageCratePrototype> {

    private final CrateMessages messages;

    @Autowired
    public PageCrateCreator(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public Crate create(PageCratePrototype prototype, Map<String, String> data) {
        PageCrate pageCrate = new PageCrate(prototype);
        getPages(prototype, data, pageCrate).forEach(pageCrate::addPage);
        return pageCrate;
    }

    private List<Page> getPages(PageCratePrototype prototype, Map<String, String> data, PageCrate pageCrate) {
        int pagesCount = countPagesForItems(prototype.getSize(), getLastItemSlot(prototype));
        List<Page> pages = new ArrayList<>();
        for (int pageCount = 1; pageCount <= pagesCount; pageCount++) {
            Page page = getPage(prototype, pageCount, pagesCount, data, pageCrate);
            pages.add(page);
        }
        return pages;
    }

    private Page getPage(PageCratePrototype prototype, int page, int maxPages, Map<String, String> data, PageCrate pageCrate) {
        int slotTo = page * prototype.getSize();
        int slotFrom = page * prototype.getSize() - prototype.getSize();

        Map<Integer, ItemCratePrototype> items = prototype.getItems().entrySet()
                .stream()
                .filter(entry -> entry.getKey() > slotFrom && entry.getKey() < slotTo)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, ItemCrate> itemCrates = getItemCrates(items, data);

        if (page > 1) {
            ItemCrate previous = getControllerItemCrate(prototype.getPreviousPageItemStack(), data, player -> pageCrate.previousPage());
            itemCrates.put(slotFrom, previous);
        }
        if (maxPages > page) {
            ItemCrate next = getControllerItemCrate(prototype.getNextPageItemStack(), data, player -> pageCrate.nextPage());
            itemCrates.put(slotTo - 1, next);
        }
        return new BasicPage(itemCrates);
    }

    private Map<Integer, ItemCrate> getItemCrates(Map<Integer, ItemCratePrototype> prototypes, Map<String, String> data) {
        return prototypes.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toItemCrate(entry.getValue(), data)));
    }

    private ItemCrate toItemCrate(ItemCratePrototype prototype, Map<String, String> data) {
        ItemStack itemStack = prototype.getItemStack();
        ItemStack preparedItemStack = prepareItemStack(itemStack, data);
        return new PersonalItemCrate(preparedItemStack, prototype, messages);
    }

    private int getLastItemSlot(PageCratePrototype prototype) {
        return prototype.getItems().keySet()
                .stream().max(Integer::compare)
                .stream()
                .findAny()
                .orElse(prototype.getSize());
    }

    private ItemCrate getControllerItemCrate(ItemStack itemStack, Map<String, String> data, Reward setPage) {
        ItemStack prepareItemStack = prepareItemStack(itemStack, data);
        List<Reward> rewards = Collections.singletonList(setPage);
        ItemCratePrototype controllerPrototype = new ItemCratePrototype(prepareItemStack, false, false, Collections.emptyList(), Collections.emptyList(), rewards);
        return new BasicItemCrate(prepareItemStack, controllerPrototype);
    }

    private int countPagesForItems(int invSize, int lastItemSlot) {
        double result = (double) lastItemSlot / invSize;
        return (int) Math.ceil(result);
    }

    @Override
    public CrateType getType() {
        return new CrateType("PAGE");
    }

}
