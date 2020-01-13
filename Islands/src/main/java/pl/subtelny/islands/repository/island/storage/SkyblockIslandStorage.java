package pl.subtelny.islands.repository.island.storage;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import pl.subtelny.beans.Autowired;
import pl.subtelny.islands.model.island.IslandCoordinates;

public class SkyblockIslandStorage {

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	@Autowired
	public SkyblockIslandStorage() {
	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

}
