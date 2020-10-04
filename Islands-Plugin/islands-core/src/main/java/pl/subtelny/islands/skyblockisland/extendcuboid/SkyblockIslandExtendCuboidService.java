package pl.subtelny.islands.skyblockisland.extendcuboid;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.repository.IslandRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOption;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.stream.Stream;

@Component
public class SkyblockIslandExtendCuboidService {

    private final SkyblockIslandSettings skyblockIslandSettings;

    private final SkyblockIslandExtendCuboidCalculator cuboidCalculator;

    private final IslandRepository islandRepository;

    @Autowired
    public SkyblockIslandExtendCuboidService(SkyblockIslandSettings skyblockIslandSettings,
                                             SkyblockIslandExtendCuboidCalculator cuboidCalculator,
                                             IslandRepository islandRepository) {
        this.skyblockIslandSettings = skyblockIslandSettings;
        this.cuboidCalculator = cuboidCalculator;
        this.islandRepository = islandRepository;
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
        islandRepository.updateIsland(skyblockIsland);
    }

    private void extendCuboidToLevel(SkyblockIslandExtendCuboidRequest request) {
        int extendLevel = request.getExtendLevel();
        SkyblockIsland skyblockIsland = request.getSkyblockIsland();
        IslandCoordinates islandCoordinates = skyblockIsland.getIslandCoordinates();

        SkyblockIslandExtendCuboidOption extendConfig = getExtendConfig(extendLevel);
        request.getPlayer().ifPresent(player -> {
            if (!request.isSkipConditions()) {
                validateConditions(player, extendConfig);
                satisfyConditions(player, extendConfig);
            }
        });
        Cuboid cuboid = cuboidCalculator.calculateCuboid(islandCoordinates, extendConfig);
        changeCuboid(skyblockIsland, extendLevel, cuboid);
    }

    public void extendCuboidToBasic(SkyblockIsland skyblockIsland, IslandCoordinates islandCoordinates) {
        Cuboid cuboid = cuboidCalculator.calculateCuboid(islandCoordinates);
        changeCuboid(skyblockIsland, 0, cuboid);
    }

    private void changeCuboid(SkyblockIsland skyblockIsland, int extendLevel, Cuboid cuboid) {
        skyblockIsland.setCuboid(cuboid);
        skyblockIsland.setExtendLevel(extendLevel);
    }

    private void validateConditions(Player player, SkyblockIslandExtendCuboidOption extendLevel) {
        Stream.concat(extendLevel.getConditions().stream(), extendLevel.getCostConditions().stream())
                .filter(condition -> !condition.satisfiesCondition(player))
                .findFirst()
                .ifPresent(this::notSatisfiedCondition);
    }

    private void notSatisfiedCondition(Condition condition) {
        MessageKey messageKey = condition.getMessageKey();
        throw ValidationException.of(messageKey.getKey(), messageKey.getObjects());
    }

    private void satisfyConditions(Player player, SkyblockIslandExtendCuboidOption extendLevel) {
        extendLevel.getCostConditions().forEach(costCondition -> costCondition.satisfyCondition(player));
    }

    private SkyblockIslandExtendCuboidOption getExtendConfig(int extendLevel) {
        return skyblockIslandSettings.getExtendCuboidLevel(extendLevel - 1)
                .orElseThrow(() -> ValidationException.of("extendSkyblockIslandCuboid.extendCuboid.extend_level_not_found", extendLevel));
    }

}
