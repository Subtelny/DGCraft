package pl.subtelny.islands.island.crate.invites;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.invites.prototype.IslandInvitesCratePrototype;

import java.util.Map;

@Component
public class IslandInvitesCrateCreator {

    private final CrateCreator<PageCratePrototype> crateCreator;

    @Autowired
    public IslandInvitesCrateCreator(CrateCreator<PageCratePrototype> crateCreator) {
        this.crateCreator = crateCreator;
    }

    public Crate create(IslandInvitesCratePrototype prototype,
                        Map<String, String> data,
                        Island island) {
        Map<Integer, ItemCratePrototype> itemCratePrototypes = new ItemInviteCrateSlotsCalculator(prototype, island).getItemCrates();
        itemCratePrototypes.putAll(prototype.getItems());

        PageCratePrototype pageCratePrototype = new PageCratePrototype(prototype.getCrateId(),
                prototype.getCrateType(),
                prototype.getTitle(),
                prototype.getPermission(),
                prototype.getSize(),
                itemCratePrototypes,
                prototype.getPreviousPageItemStack(),
                prototype.getNextPageItemStack(),
                prototype.getStaticItems());
        return crateCreator.create(pageCratePrototype, data);
    }

}
