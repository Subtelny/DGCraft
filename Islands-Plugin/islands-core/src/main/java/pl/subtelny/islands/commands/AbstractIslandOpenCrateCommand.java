package pl.subtelny.islands.commands;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.exception.ValidationException;

public abstract class AbstractIslandOpenCrateCommand extends BaseCommand {

    private final IslanderQueryService islanderQueryService;

    private final IslandModules islandModules;

    protected AbstractIslandOpenCrateCommand(IslandMessages messages,
                                             IslanderQueryService islanderQueryService,
                                             IslandModules islandModules) {
        super(messages);
        this.islanderQueryService = islanderQueryService;
        this.islandModules = islandModules;
    }

    protected void openCrate(IslandType islandType, Player player, String crate) {
        CratesComponent islandCrates = getCratesComponent(islandType);
        islandCrates.openCrate(player, crate);
    }

    protected void openCrateBasedOnIsland(IslandType islandType, Player player, String crate) {
        Islander islander = getIslander(player);
        IslandId islandId = getIslandId(islander, islandType);
        CratesComponent islandCrates = getCratesComponent(islandId.getIslandType());
        islandCrates.openCrate(player, islandId, crate);
    }

    private IslandId getIslandId(Islander islander, IslandType islandType) {
        return islander.getIsland(islandType)
                .orElseThrow(() -> ValidationException.of("command.island.island_not_found", islandType.getInternal()));
    }

    private Islander getIslander(Player player) {
        return islanderQueryService.getIslander(player);
    }

    private CratesComponent getCratesComponent(IslandType islandType) {
        return getIslandModule(islandType).getComponent(CratesComponent.class);
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return islandModules.findIslandModule(islandType)
                .orElseThrow(() -> ValidationException.of("command.island.open_crate.module_not_found", islandType.getInternal()));
    }

}
