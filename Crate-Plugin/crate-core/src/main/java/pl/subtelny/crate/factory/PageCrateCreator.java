package pl.subtelny.crate.factory;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.model.crate.page.Page;
import pl.subtelny.crate.model.crate.page.PageCrate;
import pl.subtelny.utilities.reward.Reward;

import java.util.List;
import java.util.Map;

@Component
public class PageCrateCreator implements CrateCreator<PageCratePrototype> {

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
        Reward nextPageReward = nextPageReward(pageCrate);
        Reward previousPageReward = previousPageReward(pageCrate);
        PagesFactory pagesFactory = new PagesFactory(messages, prototype, data, nextPageReward, previousPageReward);
        return pagesFactory.getPages();
    }

    private Reward nextPageReward(PageCrate pageCrate) {
        return player -> pageCrate.nextPage();
    }

    private Reward previousPageReward(PageCrate pageCrate) {
        return player -> pageCrate.previousPage();
    }

    @Override
    public CrateType getType() {
        return PageCrate.PAGE_TYPE;
    }

}
