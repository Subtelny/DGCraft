package pl.subtelny.islands.commands;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.islands.island.message.IslandMessages;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.exception.ValidationException;

public abstract class AbstractIslandOpenCrateCommand extends BaseCommand {

    private final IslanderQueryService islanderQueryService;

    private final IslandQueryService islandQueryService;

    protected AbstractIslandOpenCrateCommand(IslandMessages messages,
                                             IslanderQueryService islanderQueryService,
                                             IslandQueryService islandQueryService) {
        super(messages);
        this.islanderQueryService = islanderQueryService;
        this.islandQueryService = islandQueryService;
    }

    protected void openCrate(IslandType islandType, Player player, String crate) {
        IslandCrates islandCrates = islandQueryService.getIslandCrates(islandType);
        islandCrates.openCrate(player, crate);
    }

    protected void openCrateBasedOnIsland(IslandType islandType, Player player, String crate) {
        Islander islander = getIslander(player);
        IslandId islandId = getIslandId(islander, islandType);
        IslandCrates islandCrates = islandQueryService.getIslandCrates(islandId.getIslandType());
        islandCrates.openCrate(player, islandId, crate);
    }

    private IslandId getIslandId(Islander islander, IslandType islandType) {
        return islander.getIsland(islandType)
                .orElseThrow(() -> ValidationException.of("command.island.island_not_found", islandType.getInternal()));
    }

    private Islander getIslander(Player player) {
        return islanderQueryService.getIslander(player);
    }

}
