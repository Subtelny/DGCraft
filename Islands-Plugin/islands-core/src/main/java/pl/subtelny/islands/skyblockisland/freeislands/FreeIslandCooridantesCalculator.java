package pl.subtelny.islands.skyblockisland.freeislands;

import pl.subtelny.islands.islander.model.IslandCoordinates;

import java.util.Queue;

public interface FreeIslandCooridantesCalculator {

    Queue<IslandCoordinates> generateIslandCoordinates();

}
