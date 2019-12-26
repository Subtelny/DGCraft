package pl.subtelny.islands.repository;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.IslandCoordinates;

@Component
public class SkyblockIslandStorage {

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

}
