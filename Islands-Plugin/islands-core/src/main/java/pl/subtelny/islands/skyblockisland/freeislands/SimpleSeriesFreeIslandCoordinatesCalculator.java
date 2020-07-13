package pl.subtelny.islands.skyblockisland.freeislands;

import pl.subtelny.islands.islander.model.IslandCoordinates;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleSeriesFreeIslandCoordinatesCalculator implements FreeIslandCooridantesCalculator {

    private static final int X_MAX = 50;

    private static final int Z_MAX = 200;

    @Override
    public Queue<IslandCoordinates> generateIslandCoordinates() {
        Queue<IslandCoordinates> queue = new ConcurrentLinkedQueue<>();
        for (int z = 0; z < Z_MAX; z++) {
            for (int x = 0; x < X_MAX; x++) {
                queue.add(new IslandCoordinates(x, z));
            }
        }
        return queue;
    }

}
