package pl.subtelny.islands.skyblockisland.extendcuboid;

import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class SkyblockIslandExtendCuboidCalculator {

    private final SkyblockIslandSettings skyblockIslandSettings;

    @Autowired
    public SkyblockIslandExtendCuboidCalculator(SkyblockIslandSettings skyblockIslandSettings) {
        this.skyblockIslandSettings = skyblockIslandSettings;
    }

    public Cuboid calculateCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
        Optional<SkyblockIslandExtendCuboidOption> level = skyblockIslandSettings.getExtendCuboidLevel(extendLevel - 1);
        return level.map(level1 -> calculateCuboid(islandCoordinates, level1))
                .orElseThrow(() -> ValidationException.of("cuboidCalculator.not_found_extend_level", extendLevel));
    }

    public Cuboid calculateMaxCuboid(IslandCoordinates islandCoordinates) {
        return buildCuboid(islandCoordinates, skyblockIslandSettings.getMaxIslandSizeWithSpaceBetween());
    }

    public Cuboid calculateCuboid(IslandCoordinates islandCoordinates) {
        return buildCuboid(islandCoordinates, skyblockIslandSettings.getBasicIslandSize());
    }

    public Cuboid calculateCuboid(IslandCoordinates islandCoordinates, SkyblockIslandExtendCuboidOption level) {
        return buildCuboid(islandCoordinates, level.getIslandSize());
    }

    private Cuboid buildCuboid(IslandCoordinates islandCoordinates, int islandSize) {
        int basicIslandSize = skyblockIslandSettings.getBasicIslandSize();
        int maxIslandSize = skyblockIslandSettings.getMaxIslandSizeWithSpaceBetween();
        Validation.isTrue(islandSize <= maxIslandSize, "Tried to set islandSize: %s  when max is %s", islandSize, maxIslandSize);
        Validation.isTrue(islandSize >= basicIslandSize, "Tried to set islandSize: %s when min is %s", islandSize, basicIslandSize);

        int spaceBetweenIslands = skyblockIslandSettings.getSpaceBetweenIslands();
        World world = skyblockIslandSettings.getWorld();
        return SkyblockIslandUtil.buildCuboid(islandCoordinates, world, maxIslandSize, islandSize, spaceBetweenIslands);
    }
}
