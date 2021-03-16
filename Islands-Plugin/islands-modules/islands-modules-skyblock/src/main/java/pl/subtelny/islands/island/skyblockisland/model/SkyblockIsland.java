package pl.subtelny.islands.island.skyblockisland.model;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.skyblockisland.IslandCoordinates;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;

public class SkyblockIsland extends AbstractIsland {

    private final IslandExtendCalculator extendCalculator;

    private final IslandCoordinates islandCoordinates;

    private int extendLevel;

    public SkyblockIsland(IslandExtendCalculator extendCalculator,
                          IslandId islandId,
                          IslandType islandType,
                          LocalDateTime creationDate,
                          Cuboid cuboid,
                          Location spawn,
                          int points,
                          List<IslandMemberId> islandMemberIds,
                          IslandMemberId owner,
                          IslandCoordinates islandCoordinates,
                          int extendLevel,
                          IslandConfiguration configuration) {
        super(islandId, islandType, creationDate, cuboid, spawn, points, islandMemberIds, owner, configuration);
        Validation.isTrue(islandMemberIds.stream().allMatch(this::isIslander), "Not every islandMember is Islander!");
        this.extendCalculator = extendCalculator;
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
    }

    @Override
    public void join(IslandMember member) {
        validateIslander(member);
        super.join(member);
    }

    @Override
    public void changeBiome(Biome biome) {
        World world = cuboid.getWorld();
        int maxY = cuboid.getUpperY();
        int minY = cuboid.getLowerY();
        int maxX = cuboid.getUpperX();
        int minX = cuboid.getLowerX();
        int maxZ = cuboid.getUpperZ();
        int minZ = cuboid.getLowerZ();
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                for (int z = minZ; z < maxZ; z++) {
                    world.setBiome(x, y, z, biome);
                }
            }
        }
    }

    @Override
    public World getWorld() {
        return cuboid.getWorld();
    }

    @Override
    public String getName() {
        return "TODO name";
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public void extendIsland(int extendLevel) {
        Cuboid extendedCuboid = extendCalculator.calculateExtendedCuboid(getIslandCoordinates(), extendLevel);
        updateCuboid(extendedCuboid);
        this.extendLevel = extendLevel;
    }

    private void validateIslander(IslandMember member) {
        Validation.isTrue(isIslander(member.getIslandMemberId()), "skyblockIsland.cannot_join_not_islander");
    }

    private boolean isIslander(IslandMemberId islandMemberId) {
        return Islander.ISLAND_MEMBER_TYPE.equals(islandMemberId.getType());
    }

}
