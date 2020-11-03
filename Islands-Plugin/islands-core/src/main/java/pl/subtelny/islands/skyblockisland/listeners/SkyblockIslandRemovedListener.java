package pl.subtelny.islands.skyblockisland.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.event.IslandEventListener;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islandold.event.IslandRemovedEvent;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.region.RegionCleaner;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.cuboid.Cuboid;

@Component
public class SkyblockIslandRemovedListener implements IslandEventListener<IslandRemovedEvent> {

    private final SkyblockIslandExtendCuboidCalculator calculator;

    @Autowired
    public SkyblockIslandRemovedListener(SkyblockIslandExtendCuboidCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void handle(IslandRemovedEvent event) {
        Island island = event.getIsland();
        if (!SkyblockIsland.TYPE.equals(island.getType())) {
            return;
        }

        SkyblockIsland skyblockIsland = (SkyblockIsland) island;
        IslandCoordinates coords = skyblockIsland.getIslandCoordinates();

        Cuboid cuboid = calculator.calculateCuboid(coords);
        new RegionCleaner(cuboid).execute();
    }

}
