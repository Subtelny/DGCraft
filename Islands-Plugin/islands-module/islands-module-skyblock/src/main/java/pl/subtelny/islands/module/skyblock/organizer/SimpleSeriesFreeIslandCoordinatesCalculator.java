package pl.subtelny.islands.module.skyblock.organizer;

import pl.subtelny.islands.module.skyblock.IslandCoordinates;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SimpleSeriesFreeIslandCoordinatesCalculator {

    private static final int X_MAX = 50;

    private static final int Z_MAX = 200;

    public Deque<IslandCoordinates> generateIslandCoordinates() {
        Deque<IslandCoordinates> queue = new ConcurrentLinkedDeque<>();
        for (int z = 0; z < Z_MAX; z++) {
            for (int x = 0; x < X_MAX; x++) {
                queue.add(new IslandCoordinates(x, z));
            }
        }
        return queue;
    }

}
