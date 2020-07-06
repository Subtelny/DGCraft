package pl.subtelny.islands.skyblockisland.extendcuboid;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandSettings;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.stream.Stream;

@Component
public class SkyblockIslandExtendCuboidService {

    private final SkyblockIslandRepository skyblockIslandRepository;

    private final SkyblockIslandSettings skyblockIslandSettings;

    private final SkyblockIslandExtendCuboidCalculator cuboidCalculator;

    public SkyblockIslandExtendCuboidService(SkyblockIslandRepository skyblockIslandRepository,
                                             SkyblockIslandSettings skyblockIslandSettings,
                                             SkyblockIslandExtendCuboidCalculator cuboidCalculator) {
        this.skyblockIslandRepository = skyblockIslandRepository;
        this.skyblockIslandSettings = skyblockIslandSettings;
        this.cuboidCalculator = cuboidCalculator;
    }

    public void extendCuboid(SkyblockIslandExtendCuboidRequest request) {
        int extendLevel = request.getExtendLevel();
        SkyblockIsland skyblockIsland = request.getSkyblockIsland();
        IslandCoordinates islandCoordinates = skyblockIsland.getIslandCoordinates();

        if (extendLevel > 0) {
            extendCuboidToLevel(request);
        } else {
            extendCuboidToBasic(skyblockIsland, islandCoordinates);
        }
        skyblockIslandRepository.saveIslandAsync(skyblockIsland);
    }

    private void extendCuboidToLevel(SkyblockIslandExtendCuboidRequest request) {
        int extendLevel = request.getExtendLevel();
        SkyblockIsland skyblockIsland = request.getSkyblockIsland();
        IslandCoordinates islandCoordinates = skyblockIsland.getIslandCoordinates();

        SkyblockIslandExtendCuboidLevel extendConfig = getExtendConfig(extendLevel);
        request.getPlayer().ifPresent(player -> {
            if (!request.isSkipConditions()) {
                validateConditions(player, extendConfig);
                satisfyConditions(player, extendConfig);
            }
        });
        Cuboid cuboid = cuboidCalculator.calculateCuboid(islandCoordinates, extendConfig);
        skyblockIsland.changeCuboid(extendLevel, cuboid);
    }

    public void extendCuboidToBasic(SkyblockIsland skyblockIsland, IslandCoordinates islandCoordinates) {
        Cuboid cuboid = cuboidCalculator.calculateCuboid(islandCoordinates);
        skyblockIsland.changeCuboid(0, cuboid);
    }

    private void validateConditions(Player player, SkyblockIslandExtendCuboidLevel extendLevel) {
        Stream.concat(extendLevel.getConditions().stream(), extendLevel.getCostConditions().stream())
                .filter(condition -> !condition.satisfiesCondition(player))
                .findFirst()
                .ifPresent(this::notSatisfiedCondition);
    }

    private void notSatisfiedCondition(Condition condition) {
        MessageKey messageKey = condition.getMessageKey();
        throw ValidationException.of(messageKey.getKey(), messageKey.getObjects());
    }

    private void satisfyConditions(Player player, SkyblockIslandExtendCuboidLevel extendLevel) {
        extendLevel.getCostConditions().forEach(costCondition -> costCondition.satisfyCondition(player));
    }

    private SkyblockIslandExtendCuboidLevel getExtendConfig(int extendLevel) {
        return skyblockIslandSettings.getExtendCuboidLevel(extendLevel - 1)
                .orElseThrow(() -> ValidationException.of("extendSkyblockIslandCuboid.extendCuboid.extend_level_not_found", extendLevel));
    }

}
